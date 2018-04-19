/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.models.persistent.BasicHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.User;

/**
 * @author Apeksha
 *
 */
public class BasicHealthMetricsTest {

	@Test
	public void test() {
		BasicHealthMetrics metric = new BasicHealthMetrics();
		metric.hashCode();
		assertTrue(metric.equals(metric));
		//test getPatient
		User patient = new User();
		patient.setUsername("patient");
		metric.setPatient(patient);
		assertEquals(metric.getPatient().getUsername(), "patient");

		//test obj == null in equals
		BasicHealthMetrics metric2 = null;
		assertFalse(metric.equals(metric2));
		//initialize metric2
		metric2 = new BasicHealthMetrics();
		//test diastolic in equals
		assertFalse(metric.equals(metric2));
		metric2.setDiastolic(2);
		assertFalse(metric.equals(metric2));
		assertFalse(metric2.equals(metric));
		metric.setDiastolic(2);
		//test null hcp in equals

		//test getHCP
		User hcp = new User();
		hcp.setUsername("hcp");
		metric.setHcp(null);
		metric2.setHcp(hcp);
		assertEquals(metric2.getHcp().getUsername(), "hcp");
		assertFalse(metric.equals(metric2));



		//test null other hcp in equals
		metric2.setHcp(null);
		metric.setHcp(hcp);
		assertFalse(metric.equals(metric2));
		metric2.setHcp(hcp);


		metric.setHdl(null);
		metric2.setHdl(5);
		assertFalse(metric.equals(metric2));

		metric.setHdl(2);
		assertFalse(metric2.equals(metric));
		metric.setHdl(5);
		metric.setHeadCircumference(null);
		metric2.setHeadCircumference((float) 5);
		assertFalse(metric.equals(metric2));
		metric.setHeadCircumference((float) 6);
		assertFalse(metric.equals(metric2));
		metric2.setHeadCircumference((float) 6);


		metric.setHeight(null);
		metric2.setHeight((float) 5);
		assertFalse(metric.equals(metric2));
		metric.setHeight((float) 6);
		assertFalse(metric.equals(metric2));
		metric2.setHeight((float) 6);

		metric.setLdl(null);
		metric2.setLdl(5);
		assertFalse(metric.equals(metric2));

		metric.setLdl(2);
		assertFalse(metric2.equals(metric));
		metric.setLdl(5);

		User pat = new User();
		pat.setUsername("patient");
		User patient2 = null;
		metric.setPatient(pat);
		metric2.setPatient(patient2);
		assertFalse(metric.equals(metric2));
		assertFalse(metric2.equals(metric));
		patient2 = new User();
		patient2.setUsername("patient");
		metric2.setPatient(patient2);

		metric.setSystolic(null);
		metric2.setSystolic(5);
		assertFalse(metric.equals(metric2));
		metric.setSystolic(2);
		assertFalse(metric2.equals(metric));
		metric.setSystolic(5);

		metric.setTri(null);
		metric2.setTri(500);
		assertFalse(metric.equals(metric2));
		metric.setTri(200);
		assertFalse(metric2.equals(metric));
		metric.setTri(500);

		metric.setWeight(null);
		metric2.setWeight((float) 5);
		assertFalse(metric.equals(metric2));
		metric.setWeight((float) 6);
		assertFalse(metric.equals(metric2));
		metric2.setWeight((float) 6);

		assertTrue(metric.equals(metric2));

		assertFalse(BasicHealthMetrics.getBasicHealthMetricsForHCP(hcp.getUsername()).isEmpty());
		//assertTrue(metric.getBasicHealthMetricsForPatient(patient.getUsername()).isEmpty());
		//	assertTrue(metric.getBasicHealthMetricsForHCPAndPatient(hcp.getUsername(), patient.getUsername()).isEmpty());

		assertEquals(metric.getPatient().getUsername(), "patient");
		assertEquals(metric.getHcp().getUsername(), "hcp");
		metric.setDiastolic(null);
	}

}