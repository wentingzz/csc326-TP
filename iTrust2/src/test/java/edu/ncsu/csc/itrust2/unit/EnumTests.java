/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.BloodType;
import edu.ncsu.csc.itrust2.models.enums.Ethnicity;
import edu.ncsu.csc.itrust2.models.enums.Gender;
import edu.ncsu.csc.itrust2.models.enums.LabProcedureStatus;
import edu.ncsu.csc.itrust2.models.enums.PatientSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.enums.Status;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.enums.State;

/**
 * @author Apeksha
 *
 */
public class EnumTests {

	@Test
	public void test() {
		//Testing the Role enum class
		assertEquals(3, Role.ROLE_ADMIN.getCode());
		assertEquals("admin/index", Role.ROLE_ADMIN.getLanding());

		//Testing the TransactionType enum class
		assertEquals(650, TransactionType.APPOINTMENT_REQUEST_APPROVED.getCode());
		assertEquals("Appointment request approved by HCP", TransactionType.APPOINTMENT_REQUEST_APPROVED.getDescription());
		assertTrue(TransactionType.APPOINTMENT_REQUEST_APPROVED.isPatientViewable());

		//Checking the AppointmentType enum class
		assertEquals(1, AppointmentType.GENERAL_CHECKUP.getCode());


		//Test BloodType not specified
		assertEquals(BloodType.parse("hello"), BloodType.NotSpecified);

		//Test Ethinicity not specified
		assertEquals(Ethnicity.parse("hello"), Ethnicity.NotSpecified);

		//Test Gender not specified
		assertEquals(Gender.parse("hello"), Gender.NotSpecified);

		//Test PatientSmokingStatus invalid code
		assertEquals(PatientSmokingStatus.parseValue(6), PatientSmokingStatus.NONAPPLICABLE);

		//Testing Status enum class
		assertEquals(3, Status.APPROVED.getCode());


		//test state class
		assertEquals(State.NC, State.parse("bleh"));
		
		assertEquals(1, LabProcedureStatus.ASSIGNED.getCode());
		assertEquals(2, LabProcedureStatus.IN_PROGRESS.getCode());
		assertEquals(3, LabProcedureStatus.COMPLETED.getCode());
	

	}

}