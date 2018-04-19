/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.patient.AppointmentRequestForm;
import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.Status;
import edu.ncsu.csc.itrust2.models.persistent.AppointmentRequest;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * Tests the AppointmentRequestForm class
 * @author Apeksha
 * @author Abeer
 *
 */
public class AppointmentRequestFormTest {

	/**
	 * Test method for {@link edu.ncsu.csc.itrust2.forms.patient.AppointmentRequestForm#AppointmentRequestForm(edu.ncsu.csc.itrust2.models.persistent.AppointmentRequest)}.
	 */
	@Test
	public void testAppointmentRequestFormAppointmentRequest() {
		AppointmentRequest ar = new AppointmentRequest();
		ar.setComments("comments");
		Calendar date = Calendar.getInstance();
		ar.setDate(date);
		User hcp = new User();
		hcp.setUsername("hcp");
		ar.setHcp(hcp);
		User patient = new User();
		hcp.setUsername("patient");
		ar.setPatient(patient);
		ar.setStatus(Status.APPROVED);
		ar.setType(AppointmentType.GENERAL_CHECKUP);
		AppointmentRequestForm form = new AppointmentRequestForm(ar);
		
		form.setId("henlo");
		assertEquals("henlo", form.getId());
		
	}

}
