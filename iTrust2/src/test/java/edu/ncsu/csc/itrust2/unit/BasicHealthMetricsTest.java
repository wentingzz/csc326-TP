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
		metric.setDiastolic(2);
		metric2.setDiastolic(2);

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
		
		
		//test null hdl in equals
		metric.setHdl(null);
		metric2.setHdl(5);
		assertFalse(metric.equals(metric2));
		metric.setHdl(5);
		metric2.setHdl(null);
		assertFalse(metric.equals(metric2));
		metric2.setHdl(5);
		
		//set both heights to be equal
		metric.setHeadCircumference((float) 5);
		metric2.setHeadCircumference((float) 5);
		
		//test null height in equals
		metric.setHeight(null);
		metric2.setHeight((float) 5);
		assertFalse(metric.equals(metric2));
		//set them equal to each other
		metric.setHeight((float) 5);
		metric2.setHeight((float) 5);
		
		//test null ldl in equals
		metric.setLdl(null);
		metric2.setLdl(5);
		assertFalse(metric.equals(metric2));
		//make them equal to each other
		metric.setLdl(5);
		metric2.setPatient(patient);
		
		//test null systolic in equals
		metric.setSystolic(null);
		metric2.setSystolic(5);
		assertFalse(metric.equals(metric2));
		metric.setSystolic(5);
		
		//test null tri in equals
		metric.setTri(null);
		metric2.setTri(500);
		assertFalse(metric.equals(metric2));
		metric.setTri(500);
		
		//test null weight
		metric.setWeight(null);
		metric2.setWeight((float) 5);
		assertFalse(metric.equals(metric2));
		metric.setWeight((float) 5);
		
		
		

	}

}