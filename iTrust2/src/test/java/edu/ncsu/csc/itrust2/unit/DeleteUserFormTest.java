/**
 * 
 */
package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.admin.DeleteUserForm;

/**
 * @author Apeksha
 *
 */
public class DeleteUserFormTest {

	@Test
	public void test() {
		DeleteUserForm form = new DeleteUserForm();
		form.setName("admin");
		assertEquals(form.getName(), "admin");
		form.setConfirm("confirm");
		assertEquals(form.getConfirm(), "confirm");
	}

}