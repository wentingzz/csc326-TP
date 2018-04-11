package edu.ncsu.csc.itrust2.controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.models.enums.TransactionType;
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
        final OfficeVisit ov = OfficeVisit.getOfficeVisits().get( 0 );
        final LabProcedure lp = new LabProcedure( 4, new LabProcedureCode(), "notes", user, ov,
                User.getPatients().get( 0 ), User.getHCPs().get( 0 ), "NEW" );

        lp.save();
        System.out.println( "\n\n\n\nPass\n\n\n" );
        return LabProcedure.getAll();
    }

    /**
     * @return
     */
    @GetMapping ( BASE_PATH + "/labtechs/" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
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

    @SuppressWarnings ( "unchecked" )
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
    @PutMapping ( BASE_PATH + "/labprocedure/{id}/{status}/{labtech}/" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public ResponseEntity updateLabProcedure ( @PathVariable ( "id" ) final String id,
            @PathVariable ( "status" ) final String status, @PathVariable ( "labtech" ) final String labtech ) {
        try {
            final Long realId = Long.valueOf( id );
            final LabProcedure procedure = LabProcedure.getById( realId );
            if ( procedure == null ) {
                return new ResponseEntity( "No code with id " + id, HttpStatus.NOT_FOUND );
            }
            procedure.setStatus( status );
            procedure.setLabtech( User.getByName( labtech ) );
            procedure.save();
            final String username = LoggerUtil.currentUser();
            // try {
            // uSer = User.getByName(
            // SecurityContextHolder.getContext().getAuthentication().getName()
            // );
            // }
            // catch ( final Exception e ) {
            // // ignore, its was a test that wasn't authenticated properly.
            // }
            LoggerUtil.log( TransactionType.EIDT_LAB_PROCEDURE, username, username + " edited an Lab Procedure Code" );

            return new ResponseEntity( procedure, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not update Lab Procedure " + id + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
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
