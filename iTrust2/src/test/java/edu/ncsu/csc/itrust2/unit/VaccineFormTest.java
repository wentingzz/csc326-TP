/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.VaccineForm;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;

/**
 * Tests the Vaccine Form class
 * @author Apeksha
 * @author Abeer
 *
 */
public class VaccineFormTest {

	/**
	 * Test method for {@link edu.ncsu.csc.itrust2.forms.admin.VaccineForm#VaccineForm(edu.ncsu.csc.itrust2.models.persistent.Vaccine)}.
	 */
	@Test
	public void testVaccineFormVaccine() {
		Vaccine vaccine = new Vaccine();
		vaccine.setCode("00001");
		vaccine.setDescription("DESC");
		vaccine.setId(5L);
		vaccine.setName("Vaccine1");
		
		VaccineForm form = new VaccineForm(vaccine);
		form.setId(6L);
		assertEquals(6L, form.getId().longValue());
	}

}
