package edu.ncsu.csc.itrust2.controllers.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.forms.admin.LabProcedureCodeForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Class that provides the REST endpoints for handling Lab Procedure Codes. They
 * can be retrieved individually based on code, or all in a list. An Admin can
 * add, remove, or edit them.
 *
 * @author Azra Shaikh
 */
@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APILabProcedureCodesController extends APIController {

    /**
     * Returns a list of Codes in the system
     *
     * @return All the codes in the system
     */
    @GetMapping ( BASE_PATH + "/labprocedurecodes" )
    public List<LabProcedureCode> getCodes () {
        LoggerUtil.log( TransactionType.VIEWALL_LAB_PROCEDURE_CODE, LoggerUtil.currentUser(),
                "Fetched lab procedure codes" );
        return LabProcedureCode.getAll();
    }

    /**
     * Returns the code with the given code
     *
     * @param id
     *            The id of the code to retrieve
     * @return The requested Code
     */
    @GetMapping ( BASE_PATH + "/labprocedurecode/{id}" )
    public ResponseEntity getCode ( @PathVariable ( "id" ) final Long id ) {
        try {
            final LabProcedureCode code = LabProcedureCode.getById( id );
            if ( code == null ) {
                return new ResponseEntity( errorResponse( "No code with id " + id ), HttpStatus.NOT_FOUND );
            }
            LoggerUtil.log( TransactionType.VIEW_LAB_PROCEDURE_CODE, LoggerUtil.currentUser(),
                    "Fetched lab procedure code with id " + id );
            return new ResponseEntity( code, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not retrieve Lab Procedure Code " + id + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Updates the code with the specified code to the value supplied.
     *
     * @param id
     *            The id of the code to edit
     * @param form
     *            The new values for the Code
     * @return The Response of the action
     */
    @PutMapping ( BASE_PATH + "/labprocedurecode/{id}" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public ResponseEntity editCode ( @PathVariable ( "id" ) final Long id,
            @RequestBody final LabProcedureCodeForm form ) {
        try {
            final LabProcedureCode code = LabProcedureCode.getById( id );
            if ( code == null ) {
                return new ResponseEntity( "No code with id " + id, HttpStatus.NOT_FOUND );
            }
            form.setId( id );
            final LabProcedureCode updatedCode = new LabProcedureCode( form );
            updatedCode.setDateCreated( code.getDateCreated() );
            updatedCode.save();
            User user = null;
            try {
                user = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
            }
            catch ( final Exception e ) {
                // ignore, its was a test that wasn't authenticated properly.
            }
            LoggerUtil.log( TransactionType.EDIT_LAB_PROCEDURE_CODE, user.getUsername(),
                    user.getUsername() + " edited an Lab Procedure Code" );

            return new ResponseEntity( updatedCode, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( "Could not update Lab Procedure Code " + id + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Adds a new code to the system
     *
     * @param form
     *            The data for the new Code
     * @return The result of the action
     */
    @PostMapping ( BASE_PATH + "/labprocedurecodes" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public ResponseEntity addCode ( @RequestBody final LabProcedureCodeForm form ) {
        try {
            final LabProcedureCode code = new LabProcedureCode( form );
            code.setDateCreated( Calendar.getInstance().getTime() );
            System.out.println( code.getId() );
            code.save();
            User user = null;
            try {
                user = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
            }
            catch ( final Exception e ) {
                // ignore, its was a test that wasn't authenticated properly.
            }
            LoggerUtil.log( TransactionType.CREATE_LAB_PROCEDURE_CODE, user.getUsername(),
                    user.getUsername() + " created an Lab Procedure Code" );

            return new ResponseEntity( code, HttpStatus.OK );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return new ResponseEntity(
                    errorResponse(
                            "Could not create Lab Procedure Code " + form.getCode() + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes a code from the system.
     *
     * @param id
     *            The id of the code to delete
     * @return The result of the action.
     */
    @DeleteMapping ( BASE_PATH + "/labprocedurecode/{id}" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public ResponseEntity deleteCode ( @PathVariable ( "id" ) final Long id ) {
        try {
            final LabProcedureCode code = LabProcedureCode.getById( id );
            code.delete();
            User user = null;
            try {
                user = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
            }
            catch ( final Exception e ) {
                // ignore, its was a test that wasn't authenticated properly.
            }
            LoggerUtil.log( TransactionType.DELETE_LAB_PROCEDURE_CODE, LoggerUtil.currentUser(),
                    user.getUsername() + " deleted an Lab Procedure Code" );

            return new ResponseEntity( HttpStatus.OK );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return new ResponseEntity(
                    errorResponse( "Could not delete Lab Procedure Code " + id + " because of " + e.getMessage() ),
                    HttpStatus.BAD_REQUEST );
        }
    }

}
