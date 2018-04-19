package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.HospitalForm;
import edu.ncsu.csc.itrust2.models.enums.State;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;

/**
 * Tests for the HospitalForm class
 *
 * @author jshore
 *
 */
public class HospitalFormTest {

    /**
     * Test the HospitalForm class.
     */
    @Test
    public void testHospitalForm () {
        final Hospital hospital = new Hospital();
        hospital.setAddress( "somewhere" );
        hospital.setName( "hospital" );
        hospital.setState( State.NC );
        hospital.setZip( "27040" );
        final HospitalForm form = new HospitalForm( hospital );
        assertEquals( hospital.getAddress(), form.getAddress() );
        assertEquals( hospital.getName(), form.getName() );
        assertEquals( hospital.getState().getName(), form.getState() );
        assertEquals( hospital.getZip(), form.getZip() );
    }

    /**
     * Invalid zip codes. Reused from Guided Project
     */
    @Test
    public void testInvalidHospital () {
        final Hospital hospital = new Hospital();
        hospital.setAddress( "addr" );
        //test valid name with 20> characters, space, ', and -
        hospital.setName( "Hello-Bye's hospital" );
        hospital.setState( State.AL );
        try {
            hospital.setZip( "12345-67890" );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Only 5 or 9 digit zipcode allowed" );
        }
        try {
            hospital.setZip( null );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Only 5 or 9 digit zipcode allowed" );
        }
        try {
            hospital.setZip( "12345-123" );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Only 5 or 9 digit zipcode allowed" );
        }
        
        //test invalid hospital name longer than 20 letters
        try {
            hospital.setName( "This name is longer than twenty characters" );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Name can only contain 1 to 20 alpha characters and symbols -, ', and space" );
        }
        
      //test invalid hospital name with numbers
        try {
            hospital.setName( "hospital123" );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Name can only contain 1 to 20 alpha characters and symbols -, ', and space" );
        }
      //test invalid hospital with invalid symbols
        try {
            hospital.setName( "hospital@$" );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( e.getMessage(), "Name can only contain 1 to 20 alpha characters and symbols -, ', and space" );
        }

    }
}
