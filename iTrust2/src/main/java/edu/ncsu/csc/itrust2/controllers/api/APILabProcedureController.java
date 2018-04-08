package edu.ncsu.csc.itrust2.controllers.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.forms.personnel.PersonnelForm;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Controller responsible for providing various REST API endpoints for the
 * Personnel model.
 *
 * @author Kai Presler-Marshall
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes" } )
public class APILabProcedureController extends APIController {

    /**
     * Retrieves and returns a list of all Lab Procedures. Used when an HCP
     * documents an office visit
     *
     * @param id
     *            of the visit
     * @return list of lab procedures
     */
    @GetMapping ( BASE_PATH + "/viewlabprocedures" )
    public List<LabProcedure> getAllLabProcedures () {
        return LabProcedure.getAll();
    }

    /**
     * Retrieves and returns a list of all Lab Procedures for a visit. Used when
     * a Patient or HCP views details of an office visit
     *
     * @param id
     *            of the visit
     * @return list of lab procedures
     */
    @GetMapping ( BASE_PATH + "/labprocedures" )
    public List<LabProcedure> getLabProceduresForVisit ( final long id ) {
        return null;
    }

    /**
     * Retrieves and returns a list of all Lab Procedures for a user. Used when
     * a LabTech views the lab procedures assigned to them
     *
     * @param username
     *            of the user
     * @return list of lab procedures
     */
    @GetMapping ( BASE_PATH + "/labtech/labprocedures/" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
    public List<LabProcedure> getLabTechProcedures () {
        final User user = User.getByName( LoggerUtil.currentUser() );
        final List<LabProcedure> result = new ArrayList<LabProcedure>();
        final OfficeVisit ov = OfficeVisit.getOfficeVisits().get( 0 );
        final User patient = User.getPatients().get( 0 );
        final User hcp = User.getHCPs().get( 0 );
        result.add( new LabProcedure( 4, new LabProcedureCode(), "notes", user, ov, User.getPatients().get( 0 ),
                User.getHCPs().get( 0 ), "NEW" ) );
        return result;
    }

    @GetMapping ( BASE_PATH + "/labtechs/" )
    public List<User> getLabTechs () {
        return User.getLabtechs();
    }

    // /**
    // * View an existing Lab Procedure record
    // *
    // * @param model
    // * Date from front end
    // * @return response
    // */
    // @PostMapping ( BASE_PATH + "/viewlabprocedures" )
    // public ResponseEntity viewLabProcs ( final Model model ) {
    // return null;
    // }

    /**
     * Updates the Lab Procedure with the id provided by overwriting it with the
     * new Personnel record that is provided. If the ID provided does not match
     * the ID set in the Patient provided, the update will not take place
     *
     * @param id
     *            The username of the Lab Procedure to be updated
     * @param personnelF
     *            The updated Lab Procedure to save
     * @return response
     */
    @PutMapping ( BASE_PATH + "/labprocedure/{id}" )
    public ResponseEntity updateLabProcedure ( @PathVariable final String id,
            @RequestBody final PersonnelForm personnelF ) {
        return null;
        // final Personnel personnel = new Personnel( personnelF );
        // if ( null != personnel.getSelf() && null !=
        // personnel.getSelf().getUsername()
        // && !id.equals( personnel.getSelf().getUsername() ) ) {
        // return new ResponseEntity(
        // errorResponse( "The ID provided does not match the ID of the
        // Personnel provided" ),
        // HttpStatus.CONFLICT );
        // }
        // final Personnel dbPersonnel = Personnel.getByName( id );
        // if ( null == dbPersonnel ) {
        // return new ResponseEntity( errorResponse( "No personnel found for id
        // " + id ), HttpStatus.NOT_FOUND );
        // }
        // try {
        // personnel.save();
        // LoggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS,
        // LoggerUtil.currentUser() );
        // return new ResponseEntity( personnel, HttpStatus.OK );
        // }
        // catch ( final Exception e ) {
        // return new ResponseEntity(
        // errorResponse( "Could not update " + personnel.toString() + " because
        // of " + e.getMessage() ),
        // HttpStatus.BAD_REQUEST );
        // }
    }

    /**
     * Deletes an existing Lab Procedure record
     *
     * @param id
     *            the id of the lab Procedure to be deleted
     * @return response
     */
    @DeleteMapping ( BASE_PATH + "/labprocedure/{id}" )
    public ResponseEntity deleteLabProcedure ( @PathVariable final String id ) {
        return null;
    }
}
