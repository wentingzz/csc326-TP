/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;
import edu.ncsu.csc.itrust2.forms.admin.DeleteHospitalForm;

import org.junit.Test;

/**
 * @author Apeksha
 *
 */
public class DeleteHospitalFormTest {

	@Test
	public void test() {
		DeleteHospitalForm form = new DeleteHospitalForm();
		form.setName("admin");
		assertEquals(form.getName(), "admin");
		form.setConfirm("confirm");
		assertEquals(form.getConfirm(), "confirm");
	}

}