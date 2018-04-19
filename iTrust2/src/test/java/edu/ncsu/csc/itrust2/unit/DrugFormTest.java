/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.DrugForm;
import edu.ncsu.csc.itrust2.models.persistent.Drug;

/**
 * Tests the DrugForm class
 * @author Apeksha
 * @author Abeer
 *
 */
public class DrugFormTest {

	/**
	 * Test method for {@link edu.ncsu.csc.itrust2.forms.admin.DrugForm#DrugForm(edu.ncsu.csc.itrust2.models.persistent.Drug)}.
	 */
	@Test
	public void testDrugFormDrug() {
		Drug drug = new Drug();
		drug.setCode("0000-0000-01");
		drug.setDescription("DESC");
		drug.setId(5L);
		drug.setName("Drug");
		
		DrugForm form = new DrugForm(drug);
		form.setId(6L);
		assertEquals(6L, form.getId().longValue());
	}

}
