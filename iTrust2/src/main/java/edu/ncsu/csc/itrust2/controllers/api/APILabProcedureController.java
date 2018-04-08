package edu.ncsu.csc.itrust2.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.forms.personnel.PersonnelForm;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;

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
     * Retrieves and returns a list of all Lab Procedures for a visit
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
     * Retrieves and returns a list of all Lab Procedures for a user
     *
     * @param username
     *            of the user
     * @return list of lab procedures
     */
    @GetMapping ( BASE_PATH + "/labprocedure/{username}" )
    public List<LabProcedure> getLabProcedure ( @PathVariable ( "username" ) final String username ) {
        return null;
    }

    /**
     * Creates a new Lab Procedure record from a Lab Procedure Form
     *
     * @param labProcedureF
     *            the lab Procedure Form to be validated and saved to the
     *            database
     * @return response
     */
    @PostMapping ( BASE_PATH + "/labprocedure" )
    public ResponseEntity createPersonnel ( @RequestBody final PersonnelForm labProcedureF ) {
        return null;
        // final Personnel personnel = new Personnel( personnelF );
        // if ( null != Personnel.getByName( personnel.getSelf() ) ) {
        // return new ResponseEntity(
        // errorResponse( "Personnel with the id " + personnel.getSelf() + "
        // already exists" ),
        // HttpStatus.CONFLICT );
        // }
        // try {
        // personnel.save();
        // LoggerUtil.log( TransactionType.CREATE_DEMOGRAPHICS,
        // LoggerUtil.currentUser() );
        // return new ResponseEntity( personnel, HttpStatus.OK );
        // }
        // catch ( final Exception e ) {
        // return new ResponseEntity(
        // errorResponse( "Could not create " + personnel.toString() + " because
        // of " + e.getMessage() ),
        // HttpStatus.BAD_REQUEST );
        // }
    }

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
    public ResponseEntity updatePersonnel ( @PathVariable final String id,
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
    public ResponseEntity createPersonnel ( @PathVariable final String id ) {
        return null;
    }

    /**
     * View an existing Lab Procedure record
     *
     * @return response
     */
    @DeleteMapping ( BASE_PATH + "/viewlabprocedure" )
    public ResponseEntity viewLabProcs () {
        return null;
    }
}
