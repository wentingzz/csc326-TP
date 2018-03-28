/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.patient.AppointmentForm;

/**
 * @author Apeksha
 *
 */
public class AppointmentFormTest {

	@Test
	public void test() {
		AppointmentForm form = new AppointmentForm();
		form.setAppointment("appointment");
		assertEquals(form.getAppointment(), "appointment");
		form.setAction("action");
		assertEquals(form.getAction(), "action");
	}

}