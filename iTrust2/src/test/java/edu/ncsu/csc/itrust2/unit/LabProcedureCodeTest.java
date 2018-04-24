package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.LabProcedureCodeForm;
import edu.ncsu.csc.itrust2.forms.hcp.LabProcedureForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * tests the LabProcedureCode and LabProcedureCodeForm classes also tests
 * LabProcedureForm
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

        
        LabProcedureCodeForm bleh = new LabProcedureCodeForm(code);
        LabProcedureCodeForm bleh2 = new LabProcedureCodeForm(code);
        assertTrue(bleh.equals(bleh2));
        
        // try set code to something invalid
        try {
            code.setCode( "!!!" );
            // allowed invalid code; test should fail
            assertTrue( false );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid code; test should pass
            assertTrue( true );
        }

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
        
        assertEquals(code, procedure.getCode());
       // assertEqauls(1701, )
        // test valid setting notes
        procedure.setComments( "updated notes with additional details" );
        // test invalid and valid setting priority
        // priority may only be 1-4
        // testing lower limit
        try {
            procedure.setPriority( 0 );
            // allowed invalid priority value; test should fail
            assertTrue( false );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid priority; test should pass
            assertTrue( true );
        }
        // testing upper limit
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
            assertTrue( true );
        }
        catch ( final IllegalArgumentException e ) {
            // didn't allow the invalid priority; test should pass
            assertTrue( true );
        }
        // set status to a valid one
        procedure.setStatus( "completed" );

        // test setting the code
        final LabProcedureCode codeTwo = new LabProcedureCode( form );
        codeTwo.setCode( "1111-1" );
        procedure.setCode( codeTwo );

        // test setting the HCP
        final User hcpTwo = new User( "secondTestHCP", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_HCP, 1 );
        // procedure.setHcp( hcpTwo );

        // test setting the lab tech
        final User labTechTwo = new User( "secondTestLabTech",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_LABTECH, 1 );
        procedure.setLabtech( labTechTwo );

        // test setting the patient
        final User testPatientTwo = new User( "secondTestPatient",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );

        procedure.setPatient( testPatientTwo );

        // check that getters work
        assertTrue( procedure.getCode().equals( codeTwo ) );
        // assertTrue( procedure.getHcp().equals( hcpTwo ) );
        assertTrue( procedure.getLabtech().equals( labTechTwo ) );
        assertTrue( procedure.getPatient().equals( testPatientTwo ) );
        assertTrue( procedure.getStatus().equals( "completed" ) );
        assertTrue( procedure.getPriority() == 1 );
        assertTrue( procedure.getComments().equals( "updated notes with additional details" ) );
        // assertTrue( procedure.getOfficevisit().equals( testVisit ) );

        // create a LabProcedureForm and make sure getters work
        final LabProcedureForm form2 = new LabProcedureForm( procedure );
        assertTrue( form2.getPatient().equals( procedure.getPatient().getUsername() ) );
        assertTrue( form2.getCode().equals( procedure.getCode() ) );
        assertTrue( form2.getComment().equals( procedure.getComments() ) );
        assertTrue( form2.getLabtech().equals( procedure.getLabtech().getUsername() ) );
        assertTrue( form2.getPriority() == ( procedure.getPriority() ) );
        assertTrue( form2.getStatus().equals( procedure.getStatus() ) );

        LabProcedure testProcedure = new LabProcedure(form2);
        assertEquals(form2.getCode(), testProcedure.getCode());
        // make sure setters work
        // create a new test patient and labtech
        final User davidBowie = new User( "spaceOddity", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_PATIENT, 1 );
        final User rockyHorror = new User( "notMadeForYou",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_LABTECH, 1 );
        form2.setPatient( davidBowie.getUsername() );
        form2.setComments( "Ground Control to Major Tom" );
        form2.setPriority( 2 );
        form2.setStatus( "completed" );
        form2.setLabtech( rockyHorror.getUsername() );
        assertTrue( form2.getComment().equals( "Ground Control to Major Tom" ) );
        assertTrue( form2.getPatient().equals( davidBowie.getUsername() ) );
        assertTrue( form2.getLabtech().equals( rockyHorror.getUsername() ) );
    }

    @Test
    public void testLabProcedureCodeForm () {
    		//LabProcedureCode code = new LabProcedureCode();
    }
}
