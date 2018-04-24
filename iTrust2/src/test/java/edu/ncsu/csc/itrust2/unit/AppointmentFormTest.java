/**
 *
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.patient.AppointmentForm;
import edu.ncsu.csc.itrust2.forms.patient.AppointmentRequestForm;
import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.enums.Status;
import edu.ncsu.csc.itrust2.models.persistent.AppointmentRequest;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * @author Apeksha
 *
 */
public class AppointmentFormTest {

    @Test
    public void test () {
        final AppointmentForm form = new AppointmentForm();
        form.setAppointment( "appointment" );
        assertEquals( form.getAppointment(), "appointment" );
        form.setAction( "action" );
        assertEquals( form.getAction(), "action" );

        // test AppointmentRequestForm

        // create an AppointmentRequest in order to use the alternate
        // constructor
        final AppointmentRequest request = new AppointmentRequest();
        // create a Date to use
        final Date testDate = new Date( 9998999899L );
        // create a new test patient and hcp
        final User davidBowie = new User( "spaceOddity", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
                Role.ROLE_PATIENT, 1 );
        final User doctorFranknfurter = new User( "madScientist",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_HCP, 1 );
        // create a test date
        final Calendar date = new GregorianCalendar( 2018, 1, 14, 13, 24, 56 );
        request.setHcp( doctorFranknfurter );
        request.setPatient( davidBowie );
        request.setComments( "Ground Control to Major Tom" );
        request.setStatus( Status.APPROVED );
        request.setDate( date );
        request.setType( AppointmentType.GENERAL_CHECKUP );
        final AppointmentRequestForm testForm = new AppointmentRequestForm( request );
        testForm.setId( "002" );
        assertTrue( testForm.getId().equals( "002" ) );
    }

}
