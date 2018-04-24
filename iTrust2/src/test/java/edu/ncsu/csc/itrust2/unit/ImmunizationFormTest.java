package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.hcp.ImmunizationForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.Immunization;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;

/**
 * tests the ImmunizationForm class and the Immunization class
 *
 * @author Hannah
 *
 */
public class ImmunizationFormTest {
    @Test
    public void testImmunizationForm () {
        final ImmunizationForm form = new ImmunizationForm();
        // create a patient for the form
        final User testPatient = new User( "isThereLifeOnMars",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        // use setters
        form.setId( (long) 1 );
        form.setPatient( testPatient.getUsername() );
        form.setVaccine( "Chicken Pox" );
        // test getters
        assertTrue( form.getId().equals( (long) 1 ) );
        assertTrue( form.getPatient().equals( "isThereLifeOnMars" ) );
        assertTrue( form.getVaccine().equals( "Chicken Pox" ) );

        // create a vaccine for the Immunization to use
        final Vaccine vaccine = new Vaccine();
        vaccine.setCode( "196" );
        vaccine.setDescription( "Protects against chicken pox" );
        vaccine.setName( "Chicken Pox" );
        final Immunization chickenPox = new Immunization();
        chickenPox.setId( (long) 1 );
        chickenPox.setPatient( testPatient );
        chickenPox.setVaccine( vaccine );

        // test the other ImmunizationForm constructor
        final ImmunizationForm form2 = new ImmunizationForm( chickenPox );
        assertTrue( form2.getPatient().equals( testPatient.getUsername() ) );

    }
}
