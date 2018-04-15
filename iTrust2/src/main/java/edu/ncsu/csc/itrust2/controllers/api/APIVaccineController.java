package edu.ncsu.csc.itrust2.controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.forms.admin.VaccineForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Provides REST endpoints that deal with vaccines. Exposes functionality to
 * add, edit, fetch, and delete vaccine.
 *
 * @author Abeer Altaf
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
public class APIVaccineController extends APIController {
	/**
	 * Adds a new vaccine to the system. Requires admin permissions. Returns an
	 * error message if something goes wrong.
	 *
	 * @param form
	 *            the vaccine form
	 * @return the created vaccine
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(BASE_PATH + "/vaccines")
	public ResponseEntity addVaccine(@RequestBody final VaccineForm form) {
		try {
			final Vaccine vaccine = new Vaccine(form);
			
			// Make sure code does not conflict with existing vaccines
			if (Vaccine.getByCode(vaccine.getCode()) != null) {
				LoggerUtil.log(TransactionType.VACCINE_CREATE, LoggerUtil.currentUser(),
						"Conflict: vaccine with code " + vaccine.getCode() + " already exists");
				return new ResponseEntity(errorResponse("Vaccine with code " + vaccine.getCode() + " already exists"),
						HttpStatus.CONFLICT);
			}
			vaccine.save();
			LoggerUtil.log(TransactionType.VACCINE_CREATE, LoggerUtil.currentUser(),
					"Vaccine " + vaccine.getCode() + " created");
			return new ResponseEntity(vaccine, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.VACCINE_CREATE, LoggerUtil.currentUser(), "Failed to create vaccine");
			return new ResponseEntity(errorResponse("Could not add vaccine: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Edits a vaccine in the system. The id stored in the form must match an
	 * existing vaccine, and changes to CPT cannot conflict with existing CPTs.
	 * Requires admin permissions.
	 *
	 * @param form
	 *            the edited vaccine form
	 * @return the edited vaccine or an error message
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(BASE_PATH + "/vaccines")
	public ResponseEntity editVaccine(@RequestBody final VaccineForm form) {
		try {
			final Vaccine vaccine = new Vaccine(form);

			// Check for existing vaccine in database
			final Vaccine savedVaccine = Vaccine.getById(vaccine.getId());
			if (savedVaccine == null) {
				return new ResponseEntity(errorResponse("No vaccine found with code " + vaccine.getCode()),
						HttpStatus.NOT_FOUND);
			}

			// If the code was changed, make sure it is unique
			final Vaccine sameCode = Vaccine.getByCode(vaccine.getCode());
			if (sameCode != null && !sameCode.getId().equals(savedVaccine.getId())) {
				return new ResponseEntity(errorResponse("Vaccine with code " + vaccine.getCode() + " already exists"),
						HttpStatus.CONFLICT);
			}

			vaccine.save(); /* Overwrite existing vaccine */

			LoggerUtil.log(TransactionType.VACCINE_EDIT, LoggerUtil.currentUser(),
					"Vaccine with id " + vaccine.getId() + " edited");
			return new ResponseEntity(vaccine, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.VACCINE_EDIT, LoggerUtil.currentUser(), "Failed to edit vaccine");
			return new ResponseEntity(errorResponse("Could not update vaccine: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes the vaccine with the id matching the given id. Requires admin
	 * permissions.
	 *
	 * @param id
	 *            the id of the vaccine to delete
	 * @return the id of the deleted vaccine
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(BASE_PATH + "/vaccines/{id}")
	public ResponseEntity deleteVaccine(@PathVariable final String id) {
		try {
			final Vaccine vaccine = Vaccine.getById(Long.parseLong(id));
			if (vaccine == null) {
				LoggerUtil.log(TransactionType.VACCINE_DELETE, LoggerUtil.currentUser(),
						"Could not find vaccine with id " + id);
				return new ResponseEntity(errorResponse("No vaccine found with id " + id), HttpStatus.NOT_FOUND);
			}
			vaccine.delete();
			LoggerUtil.log(TransactionType.VACCINE_DELETE, LoggerUtil.currentUser(),
					"Deleted vaccine with id " + vaccine.getId());
			return new ResponseEntity(id, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.VACCINE_DELETE, LoggerUtil.currentUser(), "Failed to delete vaccine");
			return new ResponseEntity(errorResponse("Could not delete vaccine: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Gets a list of all the vaccines in the system.
	 *
	 * @return a list of vaccines
	 */
	@GetMapping(BASE_PATH + "/vaccines")
	public List<Vaccine> getVaccines() {
		LoggerUtil.log(TransactionType.VACCINE_VIEW, LoggerUtil.currentUser(), "Fetched list of vaccines");
		return Vaccine.getAll();
	}

}
