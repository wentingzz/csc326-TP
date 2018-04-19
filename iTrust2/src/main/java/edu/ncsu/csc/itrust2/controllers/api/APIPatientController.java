package edu.ncsu.csc.itrust2.controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import edu.ncsu.csc.itrust2.forms.hcp_patient.PatientForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Controller responsible for providing various REST API endpoints for the
 * Patient model.
 *
 * @author Kai Presler-Marshall
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIPatientController extends APIController {

    /**
     * Retrieves and returns a list of all Patients stored in the system
     *
     * @return list of patients
     */
    @GetMapping ( BASE_PATH + "/patients" )
    public List<Patient> getPatients () {
        final List<Patient> list = Patient.getPatients();
        list.remove( Patient
                .getPatient( User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() ) ) );
        return list;
    }

    /**
     * If you are logged in as a patient, then you can use this convenience
     * lookup to find your own information without remembering your id. This
     * allows you the shorthand of not having to look up the id in between.
     *
     * @return The patient object for the currently authenticated user.
     */
    @GetMapping ( BASE_PATH + "/patient" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public ResponseEntity getPatient () {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        final Patient patient = Patient.getPatient( self );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "Could not find a patient entry for you, " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            LoggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, LoggerUtil.currentUser(), self.getUsername(),
                    "Retrieved demographics for user " + self.getUsername() );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Retrieves and returns the Patient with the username provided
     *
     * @param username
     *            The username of the Patient to be retrieved, as stored in the
     *            Users table
     * @return response
     */
    @GetMapping ( BASE_PATH + "/patients/{username}" )
    public ResponseEntity getPatient ( @PathVariable ( "username" ) final String username ) {
        final Patient patient = Patient.getByName( username );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + username ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            LoggerUtil.log( TransactionType.PATIENT_DEMOGRAPHICS_VIEW, LoggerUtil.currentUser(), username,
                    "HCP retrieved demographics for patient with username " + username );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Creates a new Patient record for a User from the RequestBody provided.
     *
     * @param patientF
     *            the Patient to be validated and saved to the database
     * @return response
     */
    @PostMapping ( BASE_PATH + "/patients" )
    public ResponseEntity createPatient ( @RequestBody final PatientForm patientF ) {
        try {
            final Patient patient = new Patient( patientF );
            if ( null != Patient.getPatient( patient.getSelf() ) ) {
                return new ResponseEntity(
                        errorResponse( "Patient with the id " + patient.getSelf().getUsername() + " already exists" ),
                        HttpStatus.CONFLICT );
            }
            patient.save();
            LoggerUtil.log( TransactionType.CREATE_DEMOGRAPHICS, LoggerUtil.currentUser() );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not create " + patientF.toString() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }

    }

    /**
     * Updates the Patient with the id provided by overwriting it with the new
     * Patient record that is provided. If the ID provided does not match the ID
     * set in the Patient provided, the update will not take place
     *
     * @param id
     *            The username of the Patient to be updated
     * @param patientF
     *            The updated Patient to save
     * @return response
     */
    @PutMapping ( BASE_PATH + "/patients/{id}" )
    public ResponseEntity updatePatient ( @PathVariable final String id, @RequestBody final PatientForm patientF ) {
        // check that the user is an HCP or a patient with username equal to id
        boolean userEdit = false; // true if user edits his or her own
                                  // demographics, false if hcp edits them
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if ( !auth.getAuthorities().contains( new SimpleGrantedAuthority( "ROLE_HCP" ) )
                    && ( !auth.getAuthorities().contains( new SimpleGrantedAuthority( "ROLE_PATIENT" ) )
                            || !auth.getName().equals( id ) ) ) {
                return new ResponseEntity( errorResponse( "You do not have permission to edit this record" ),
                        HttpStatus.UNAUTHORIZED );
            }

            userEdit = auth.getAuthorities().contains( new SimpleGrantedAuthority( "ROLE_HCP" ) ) ? true : false;
        }
        catch ( final Exception e ) {
            return new ResponseEntity( HttpStatus.UNAUTHORIZED );
        }

        try {
            final Patient patient = new Patient( patientF );
            if ( null != patient.getSelf().getUsername() && !id.equals( patient.getSelf().getUsername() ) ) {
                return new ResponseEntity(
                        errorResponse( "The ID provided does not match the ID of the Patient provided" ),
                        HttpStatus.CONFLICT );
            }
            final Patient dbPatient = Patient.getByName( id );
            if ( null == dbPatient ) {
                return new ResponseEntity( errorResponse( "No Patient found for id " + id ), HttpStatus.NOT_FOUND );
            }
            patient.save();

            // Log based on whether user or hcp edited demographics
            if ( userEdit ) {
                LoggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS, LoggerUtil.currentUser(),
                        "User with username " + patient.getSelf().getUsername() + "updated their demographics" );
            }
            else {
                LoggerUtil.log( TransactionType.PATIENT_DEMOGRAPHICS_EDIT, LoggerUtil.currentUser(),
                        patient.getSelf().getUsername(),
                        "HCP edited demographics for patient with username " + patient.getSelf().getUsername() );
            }
            return new ResponseEntity( patient, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not update " + patientF.toString() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Gets the personal representatives for the user
     *
     * @return list of patient representatives
     */
    @GetMapping ( BASE_PATH + "/patient/representatives" )
    public List<Patient> getPersonalRepresentatives () {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        if ( self == null ) {
            return null;
        }
        final Patient patient = Patient.getPatient( self );
        final List<String> list1 = Lists.newArrayList( patient.getPersonalRepresentatives() );
        final List<Patient> list = Lists.newArrayList();
        for ( int i = 0; i < list1.size(); i++ ) {
            list.add( Patient.getByName( list1.get( i ) ) );
        }

        return list;
    }

    /**
     * Gets the patients this user represents
     *
     * @return the list of patients this user represents
     */
    @GetMapping ( BASE_PATH + "/patient/represented" )
    public List<Patient> getRepresented () {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        final Patient patient = Patient.getPatient( self );
        final List<String> names = Lists.newArrayList( patient.getRepresented() );
        final List<Patient> list = Lists.newArrayList();
        for ( int i = 0; i < names.size(); i++ ) {
            list.add( Patient.getByName( names.get( i ) ) );
        }
        return list;
    }

    /**
     * Undeclares the representative for patient
     *
     * @param representative
     *            the username of the patient
     * @return response entity
     */
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    @DeleteMapping ( BASE_PATH + "/patient/{representative}" )
    public ResponseEntity undeclarePersonalRepresentative (
            @PathVariable ( "representative" ) final String representative ) {
        final String username = LoggerUtil.currentUser();
        final User self = User.getByName( username );
        Patient patient = Patient.getPatient( self );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            patient.undeclarePersonalRepresentative( representative );
            patient.save();
            patient = Patient.getPatient( User.getByName( representative ) );
            patient.undeclareRepresented( username );
            patient.save();
            LoggerUtil.log( TransactionType.DECLARE_PERSONAL_REPRESENTATIVES, LoggerUtil.currentUser(),
                    "Patient  " + patient + "undeclared " + representative );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Declares the representative for patient
     *
     * @param representative
     *            the personal representative to be added
     * @return response entity
     */
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    @PutMapping ( BASE_PATH + "/patient/addrepresentative/{representative}" )
    public ResponseEntity declarePersonalRepresentative (
            @PathVariable ( "representative" ) final String representative ) {
        final String username = LoggerUtil.currentUser();
        final User self = User.getByName( username );
        Patient patient = Patient.getPatient( self );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            patient.addPersonalRepresentative( representative );
            patient.save();
            patient = Patient.getPatient( User.getByName( representative ) );
            patient.addRepresented( username );
            patient.save();
            LoggerUtil.log( TransactionType.UNDECLARE_PERSONAL_REPRESENTATIVES, LoggerUtil.currentUser(),
                    "Patient " + patient + "declared representative  + representative" );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Undeclare who they are representing
     *
     * @param represented
     *            the patient's representative
     *
     * @return response entity
     */
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    @DeleteMapping ( BASE_PATH + "/patient/removerepresented/{represented}" )
    public ResponseEntity undeclareRepresented ( @PathVariable ( "represented" ) final String represented ) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User self = User.getByName( username );
        Patient patient = Patient.getPatient( self );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            patient.undeclareRepresented( represented );
            patient.save();
            patient = Patient.getPatient( User.getByName( represented ) );
            patient.undeclarePersonalRepresentative( username );
            patient.save();
            LoggerUtil.log( TransactionType.DECLARE_PERSONAL_REPRESENTATIVES, LoggerUtil.currentUser(),
                    self.getUsername(), "Patient  " + patient + "undeclared " + represented );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }

    /**
     * Gets the personal representatives for a user. HCP view
     *
     * @param user
     *            the user whose personal representatives are to be retrieved
     * @return list of personal representatives for a user
     */
    @PreAuthorize ( "hasRole('ROLE_HCP')" )
    @GetMapping ( BASE_PATH + "/patient/representatives/{user}" )
    public List<Patient> getUserRepresentatives ( @PathVariable ( "user" ) final String user ) {
        final User self = User.getByName( user );
        if ( self == null ) {
            return null;
        }
        final Patient patient = Patient.getPatient( self );
        final List<String> list1 = Lists.newArrayList( patient.getPersonalRepresentatives() );
        final List<Patient> list = Lists.newArrayList();
        for ( int i = 0; i < list1.size(); i++ ) {
            list.add( Patient.getByName( list1.get( i ) ) );
        }
        return list;
    }

    /**
     * Gets the patients this user represents. HCP view
     *
     * @param user
     *            the given username
     * @return the list of patients the given user represents
     */
    @PreAuthorize ( "hasRole('ROLE_HCP')" )
    @GetMapping ( BASE_PATH + "/patient/represented/{user}" )
    public List<Patient> getUserRepresented ( @PathVariable ( "user" ) final String user ) {
        final User self = User.getByName( user );
        final Patient patient = Patient.getPatient( self );
        final List<String> names = Lists.newArrayList( patient.getRepresented() );
        final List<Patient> list = Lists.newArrayList();
        for ( int i = 0; i < names.size(); i++ ) {
            list.add( Patient.getByName( names.get( i ) ) );
        }
        return list;
    }

    /**
     * HCP declares the representative for a patient
     *
     * @param username
     *            the given username
     * @param representative
     *            the given personal representative
     * @return response entity
     */
    @PreAuthorize ( "hasRole('ROLE_HCP')" )
    @PutMapping ( BASE_PATH + "/patient/{user}/{representative}/addrepresentative" )
    public ResponseEntity declarePersonalRepresentativeHCP ( @PathVariable ( "user" ) final String username,
            @PathVariable ( "representative" ) final String representative ) {
        final User self = User.getByName( username );
        Patient patient = Patient.getPatient( self );
        if ( patient == null ) {
            return new ResponseEntity( errorResponse( "No Patient found for username " + self.getUsername() ),
                    HttpStatus.NOT_FOUND );
        }
        else {
            patient.addPersonalRepresentative( representative );
            patient.save();
            patient = Patient.getPatient( User.getByName( representative ) );
            patient.addRepresented( username );
            patient.save();
            LoggerUtil.log( TransactionType.UNDECLARE_PERSONAL_REPRESENTATIVES, LoggerUtil.currentUser(),
                    "Patient " + patient + "declared representative  + representative" );
            return new ResponseEntity( patient, HttpStatus.OK );
        }
    }
}
