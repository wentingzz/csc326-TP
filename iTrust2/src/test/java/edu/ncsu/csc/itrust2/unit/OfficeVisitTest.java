package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.itrust2.models.persistent.BasicHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.Diagnosis;
import edu.ncsu.csc.itrust2.models.persistent.Drug;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.ICDCode;
import edu.ncsu.csc.itrust2.models.persistent.Immunization;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.Prescription;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class OfficeVisitTest {

    @Test
    public void testOfficeVisit () {

        final Hospital hosp = new Hospital( "Dr Jenkins' Asylum", "123 Main St", "12345", "NC" );
        hosp.save();

        final OfficeVisit visit = new OfficeVisit();

        final BasicHealthMetrics bhm = new BasicHealthMetrics();

        bhm.setDiastolic( 150 );
        bhm.setDiastolic( 100 );
        bhm.setHcp( User.getByName( "hcp" ) );
        bhm.setPatient( User.getByName( "AliceThirteen" ) );
        bhm.setHdl( 75 );
        bhm.setHeight( 75f );
        bhm.setHouseSmokingStatus( HouseholdSmokingStatus.NONSMOKING );

        bhm.save();

        final Calendar time = Calendar.getInstance();
        visit.setBasicHealthMetrics( bhm );
        visit.setType( AppointmentType.GENERAL_CHECKUP );
        visit.setHospital( hosp );
        visit.setPatient( User.getByName( "AliceThirteen" ) );
        visit.setHcp( User.getByName( "AliceThirteen" ) );
        visit.setDate( time );
        visit.setNotes( "comment" );
        visit.save();

        assertTrue( visit.getHospital().getName().equals( "Dr Jenkins' Asylum" ) );
        assertTrue( visit.getNotes().equals( "comment" ) );
        assertTrue( time.equals( visit.getDate() ) );

        final List<Diagnosis> diagnoses = new Vector<Diagnosis>();

        final ICDCode code = new ICDCode();
        code.setCode( "A21" );
        code.setDescription( "Top Quality" );

        code.save();

        final Diagnosis diagnosis = new Diagnosis();

        diagnosis.setCode( code );
        diagnosis.setNote( "This is bad" );
        diagnosis.setVisit( visit );

        diagnoses.add( diagnosis );

        visit.setDiagnoses( diagnoses );

        visit.save();
        // test the getter for appointments
        assertNull( visit.getAppointment() );
        // test the getter for the office visit's hospital
        assertTrue( visit.getHospital().getName().equals( "Dr Jenkins' Asylum" ) );
        // test the getter for the type of appointment
        assertTrue( visit.getType().equals( AppointmentType.GENERAL_CHECKUP ) );
        assertTrue( visit.getLabProcedures().isEmpty() );

        final ArrayList<LabProcedure> lplist = new ArrayList<LabProcedure>();
        lplist.add( new LabProcedure() );
        visit.setLabProcedures( lplist );
        assertTrue( visit.getLabProcedures().size() == 1 );
        visit.setLabProcedures( Collections.emptyList() );
        final ArrayList<Immunization> ilist = new ArrayList<Immunization>();
        ilist.add( new Immunization() );
        visit.setImmunizations( ilist );
        assertTrue( visit.getImmunizations().size() == 1 );
        visit.setImmunizations( Collections.emptyList() );

        final Drug drug = new Drug();

        drug.setCode( "1234-4321-89" );
        drug.setDescription( "Lithium Compounds" );
        drug.setName( "Li2O8" );
        drug.save();

        final Prescription pres = new Prescription();
        pres.setDosage( 3 );
        pres.setDrug( drug );

        final Calendar end = Calendar.getInstance();
        end.add( Calendar.DAY_OF_WEEK, 10 );
        pres.setEndDate( end );
        pres.setPatient( User.getByName( "AliceThirteen" ) );
        pres.setStartDate( Calendar.getInstance() );
        pres.setRenewals( 5 );

        pres.save();

        visit.setPrescriptions( Collections.singletonList( pres ) );

        visit.save();

        visit.setPrescriptions( Collections.emptyList() );

        visit.save();

        visit.delete();
    }

}
