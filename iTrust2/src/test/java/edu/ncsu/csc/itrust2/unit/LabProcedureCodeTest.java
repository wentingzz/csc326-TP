package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.LabProcedureCodeForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * tests the LabProcedureCode and LabProcedureCodeForm classes
 *
 * @author Hannah
 *
 */
public class LabProcedureCodeTest {
    @Test
    // create a LabProcedureCodeForm
    public void createFormAndCodeAndProcedure () {
        // make the test date a random constant date/time;
        // to use the current date/time, can use empty Date() constructor
        final Date testDate = new Date( 9998999899L );
        // create a form with valid information filled in
        final LabProcedureCodeForm form = new LabProcedureCodeForm();
        form.setId( (long) 1 );
        form.setCode( "88573-1" );
        form.setLongCommonName( "Onchocherca sp IgG2 Ab [Presence] in Serum by Immunoassay" );
        form.setComponent( "Onchochera sp Ab.IgG2" );
        form.setProperty( "PrThr" );
        form.setDateCreated( testDate );

        // verify the setters work properly
        assertTrue( form.getLongCommonName().equals( "Onchocherca sp IgG2 Ab [Presence] in Serum by Immunoassay" ) );
        assertTrue( form.getComponent().equals( "Onchochera sp Ab.IgG2" ) );
        assertTrue( form.getProperty().equals( "PrThr" ) );
        assertTrue( form.getDateCreated().equals( testDate ) );
        assertTrue( form.getId().equals( ( (long) 1 ) ) );

        // make a LabProcedureCode object using the form created above
        final LabProcedureCode code = new LabProcedureCode( form );
        // verify the information used to create it is correct
        assertTrue( code.getLongCommonName().equals( "Onchocherca sp IgG2 Ab [Presence] in Serum by Immunoassay" ) );
        assertTrue( code.getComponent().equals( "Onchochera sp Ab.IgG2" ) );
        assertTrue( code.getProperty().equals( "PrThr" ) );
        assertTrue( code.getDateCreated().equals( testDate ) );

        // create the users needed to make a LabProcedure, plus the office visit
        final User labTech = new User( "labTech1", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_LABTECH, 1 );
        final User testPatient = new User( "testPatient1",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        final User testHCP = new User( "testHCP1", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_HCP, 1 );
        final OfficeVisit testVisit = new OfficeVisit();

        // create a valid LabProcedure using the LabProcedureCode
        final LabProcedure procedure = new LabProcedure( 4, code, "scheduled test", labTech, testVisit, testPatient,
                testHCP, "in-progress" );

        // test valid setting notes
        procedure.setNotes( "updated notes with additional details" );
        // test invalid and valid setting priority
        // priority may only be 1-4
        try {
            procedure.setPriority( 0 );
            // allowed invalid priority value; test should fail
            assertTrue( false );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid priority; test should pass
            assertTrue( true );
        }
        try {
            procedure.setPriority( 5 );
            // allowed invalid priority value; test should fail
            assertTrue( false );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid priority; test should pass
            assertTrue( true );
        }
        // try setting priority to a valid integer
        procedure.setPriority( 1 );

        // set status to something invalid
        try {
            procedure.setStatus( "being worked on" );
            // allowed invalid status; test should fail
            assertTrue( false );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid priority; test should pass
            assertTrue( true );
        }
        // set status to a valid one
        procedure.setStatus( "completed" );
    }
}
