package edu.ncsu.csc.itrust2.utils;

import java.util.Calendar;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.enums.State;
import edu.ncsu.csc.itrust2.models.persistent.Drug;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.Personnel;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;

/**
 * Newly revamped Test Data Generator. This class is used to generate database
 * records for the various different types of persistent objects that exist in
 * the system. Takes advantage of Hibernate persistence. To use, instantiate the
 * type of object in question, set all of its parameters, and then call the
 * save() method on the object.
 *
 * @author Kai Presler-Marshall
 *
 */
public class HibernateDataGenerator {

    /**
     * Starts the data generator program.
     *
     * @param args
     *            command line arguments
     */
    public static void main ( final String args[] ) {
        refreshDB();
        generateUsers();

        System.exit( 0 );
        return;
    }

    /**
     * Generate sample users for the iTrust2 system.
     */
    public static void refreshDB () {
        // using the config to drop/create taken from here:
        // https://stackoverflow.com/questions/20535423/how-to-manually-invoke-create-drop-from-jpa-on-hibernate
        // how to actually generate the schemaexport taken from here:
        // http://www.javarticles.com/2015/06/generating-database-schema-using-hibernate.html
        final SchemaExport export = new SchemaExport( (MetadataImplementor) new MetadataSources(
                new StandardServiceRegistryBuilder().configure( "/hibernate.cfg.xml" ).build() ).buildMetadata() );
        export.drop( true, true );
        export.create( true, true );

        // TODO we might need to add a delay here

        generateUsers();

        final Patient tim = new Patient();
        final User timUser = new User( "TimTheOneYearOld",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        timUser.save();
        tim.setSelf( timUser );
        tim.setFirstName( "TimTheOneYearOld" );
        tim.setLastName( "Smith" );
        final Calendar timBirth = Calendar.getInstance();
        timBirth.add( Calendar.YEAR, -1 ); // tim is one year old
        tim.setDateOfBirth( timBirth );
        tim.save();

        final Patient bob = new Patient();
        bob.setFirstName( "BobTheFourYearOld" );
        final User bobUser = new User( "BobTheFourYearOld",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        bobUser.save();
        bob.setSelf( bobUser );
        bob.setLastName( "Smith" );
        final Calendar bobBirth = Calendar.getInstance();
        bobBirth.add( Calendar.YEAR, -4 ); // bob is four years old
        bob.setDateOfBirth( bobBirth );
        bob.save();

        final Patient alice = new Patient();
        alice.setFirstName( "AliceThirteen" );
        final User aliceUser = new User( "AliceThirteen",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        aliceUser.save();
        alice.setSelf( aliceUser );
        alice.setLastName( "Smith" );
        final Calendar aliceBirth = Calendar.getInstance();
        aliceBirth.add( Calendar.YEAR, -13 ); // alice is thirteen years old
        alice.setDateOfBirth( aliceBirth );
        alice.save();

        final Hospital hosp = new Hospital( "General Hostpital", "123 Main St", "12345", "NC" );
        hosp.save();
    }

    /**
     * Generate sample users for the iTrust2 system.
     */
    public static void generateUsers () {
        final User hcp = new User( "hcp", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_HCP,
                1 );
        hcp.save();

        final Personnel p = new Personnel();
        p.setSelf( hcp );
        p.setFirstName( "HCP" );
        p.setLastName( "HCP" );
        p.setEmail( "csc326.201.1@gmail.com" );
        p.setAddress1( "1234 Road St." );
        p.setCity( "town" );
        p.setState( State.AK );
        p.setZip( "12345" );
        p.setPhone( "111-222-3333" );
        p.save();
        
        final User er = new User( "er", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_ER, 1 );
        er.save();
        
        final User labtech = new User( "labtech", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_LABTECH	, 1 );
        labtech.save();
        
        final User patient = new User( "patient", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_PATIENT, 1 );
        patient.save();
        // this is Patient object corresponding to the user 'patient'
        final Patient patientPatient = new Patient();
        // sync the User 'patient' with the Patient 'patientPatient'
        patientPatient.setSelf( patient );
        patientPatient.save();

        final User admin = new User( "admin", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_ADMIN, 1 );
        admin.save();

        final User alminister = new User( "Al Minister", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_ADMIN, 1 );
        alminister.save();

        final User jbean = new User( "jbean", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_PATIENT, 1 );
        jbean.save();

        final User nsanderson = new User( "nsanderson", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_PATIENT, 1 );
        nsanderson.save();

        final User svang = new User( "svang", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_HCP, 1 );
        svang.save();

        // generate users for testing password change & reset
        for ( int i = 1; i <= 5; i++ ) {
            final User pwtestuser = new User( "pwtestuser" + i,
                    "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_HCP, 1 );
            pwtestuser.save();
        }

        final User lockoutUser = new User( "lockoutUser",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_HCP, 1 );
        lockoutUser.save();

        final User lockoutUser2 = new User( "lockoutUser2",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_HCP, 1 );
        lockoutUser2.save();

        final Patient csc326 = new Patient();
        csc326.setFirstName( "cscThreeTwentySix" );
        final User csc326User = new User( "cscThreeTwentySix", "$2a$10$hOCH0uJlfbR6xzKWPQToXu1RP1/yLAngFXbVKhcnteRIQ1r/bGflm",
                Role.ROLE_PATIENT, 1 );
        csc326User.save();
        csc326.setSelf( csc326User );
        csc326.setLastName( "User" );
        csc326.setEmail( "csc326s18.203.02@gmail.com" );
        final Calendar csc326Birth = Calendar.getInstance();
        csc326Birth.add( Calendar.YEAR, -13 );
        csc326.setDateOfBirth( csc326Birth );
        csc326.save();

        final Patient testER = new Patient();
        testER.setFirstName( "ER" );
        final User testERUser = new User( "testeruser", "$2a$10$hOCH0uJlfbR6xzKWPQToXu1RP1/yLAngFXbVKhcnteRIQ1r/bGflm",
                Role.ROLE_ER, 1 );
        testERUser.save();
        testER.setSelf( testERUser );
        testER.setLastName( "User" );
        testER.setEmail( "csc326s18.203.02@gmail.com" );
        final Calendar testeruserBirth = Calendar.getInstance();
        testeruserBirth.add( Calendar.YEAR, -13 );
        testER.setDateOfBirth( testeruserBirth );
        testER.save();

        final Patient testTech = new Patient();
        testTech.setFirstName( "cscThreeTwentySix" );
        final User testTechUser = new User( "testlabtech",
                "$2a$10$hOCH0uJlfbR6xzKWPQToXu1RP1/yLAngFXbVKhcnteRIQ1r/bGflm", Role.ROLE_ER, 1 );
        testTechUser.save();
        testTech.setSelf( testTechUser );
        testTech.setLastName( "User" );
        testTech.setEmail( "csc326s18.203.02@gmail.com" );
        final Calendar testtechBirth = Calendar.getInstance();
        testeruserBirth.add( Calendar.YEAR, -13 );
        testTech.setDateOfBirth( testtechBirth );
        testTech.save();

    }

    /**
     * Generates the patients, hospitals, drugs, etc. needed for testing.
     */
    public static void generateTestFaculties () {
        final Patient tim = new Patient();
        final User timUser = new User( "TimTheOneYearOld",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        timUser.save();
        tim.setSelf( timUser );
        tim.setFirstName( "TimTheOneYearOld" );
        tim.setLastName( "Smith" );
        final Calendar timBirth = Calendar.getInstance();
        timBirth.add( Calendar.YEAR, -1 ); // tim is one year old
        tim.setDateOfBirth( timBirth );
        tim.save();

        final Patient bob = new Patient();
        bob.setFirstName( "BobTheFourYearOld" );
        final User bobUser = new User( "BobTheFourYearOld",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        bobUser.save();
        bob.setSelf( bobUser );
        bob.setLastName( "Smith" );
        final Calendar bobBirth = Calendar.getInstance();
        bobBirth.add( Calendar.YEAR, -4 ); // bob is four years old
        bob.setDateOfBirth( bobBirth );
        bob.save();

        final Patient alice = new Patient();
        alice.setFirstName( "AliceThirteen" );
        final User aliceUser = new User( "AliceThirteen",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        aliceUser.save();
        alice.setSelf( aliceUser );
        alice.setLastName( "Smith" );
        final Calendar aliceBirth = Calendar.getInstance();
        aliceBirth.add( Calendar.YEAR, -13 ); // alice is thirteen years old
        alice.setDateOfBirth( aliceBirth );
        alice.save();

        final Hospital hosp = new Hospital( "General Hospital", "123 Main St", "12345", "NC" );
        hosp.save();

        final Drug d = new Drug();
        d.setCode( "1000-0001-10" );
        d.setName( "Quetiane Fumarate" );
        d.setDescription( "atypical antipsychotic and antidepressant" );
        d.save();
        
        final Vaccine v = new Vaccine();
        v.setCode("90281");
        v.setName("IG");
        v.setDescription("Immune Globulin");
        v.save();
    }
}
