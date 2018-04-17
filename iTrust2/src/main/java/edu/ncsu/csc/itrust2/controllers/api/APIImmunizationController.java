package edu.ncsu.csc.itrust2.controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.forms.hcp.ImmunizationForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.Immunization;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Provides REST endpoints that deal with immunizations. Exposes functionality
 * to add, edit, fetch, and delete immunizations.
 *
 * @author Abeer Altaf
 * @author Apeksha Awasthi
 */
@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class APIImmunizationController extends APIController {
	/**
	 * Adds a new immunization to the system. Requires HCP permissions.
	 *
	 * @param form
	 *            details of the new immunization
	 * @return the created immunization
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@PostMapping(BASE_PATH + "/immunizations")
	public ResponseEntity addImmunization(@RequestBody final ImmunizationForm form) {
		try {
			final Immunization i = new Immunization(form);
			i.save();
			LoggerUtil.log(TransactionType.IMMUNIZATION_CREATE, LoggerUtil.currentUser(), i.getPatient().getUsername(),
					"Created immunization with id " + i.getId());
			return new ResponseEntity(i, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.IMMUNIZATION_CREATE, LoggerUtil.currentUser(),
					"Failed to create immunization");
			return new ResponseEntity(errorResponse("Could not save the immunization: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Edits an existing immunization in the system. Matches immunizations by ids.
	 * Requires HCP permissions.
	 *
	 * @param form
	 *            the form containing the details of the new immunization
	 * @return the edited immunization
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@PutMapping(BASE_PATH + "/immunizations")
	public ResponseEntity editImmunization(@RequestBody final ImmunizationForm form) {
		try {
			final Immunization i = new Immunization(form);
			final Immunization saved = Immunization.getById(i.getId());
			if (saved == null) {
				LoggerUtil.log(TransactionType.IMMUNIZATION_EDIT, LoggerUtil.currentUser(),
						"No immunization found with id " + i.getId());
				return new ResponseEntity(errorResponse("No immunization found with id " + i.getId()),
						HttpStatus.NOT_FOUND);
			}
			i.save(); /* Overwrite existing */
			LoggerUtil.log(TransactionType.IMMUNIZATION_EDIT, LoggerUtil.currentUser(), i.getPatient().getUsername(),
					"Edited immunization with id " + i.getId());
			return new ResponseEntity(i, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.IMMUNIZATION_EDIT, LoggerUtil.currentUser(), "Failed to edit immunization");
			return new ResponseEntity(errorResponse("Failed to update immunization: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes the immunization with the given id.
	 *
	 * @param id
	 *            the id
	 * @return the id of the deleted immunization
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@DeleteMapping(BASE_PATH + "/immunizations/{id}")
	public ResponseEntity deleteImmunization(@PathVariable final Long id) {
		final Immunization i = Immunization.getById(id);
		if (i == null) {
			return new ResponseEntity(errorResponse("No immunization found with id " + id), HttpStatus.NOT_FOUND);
		}
		try {
			i.delete();
			LoggerUtil.log(TransactionType.IMMUNIZATION_DELETE, LoggerUtil.currentUser(), i.getPatient().getUsername(),
					"Deleted immunization with id " + i.getId());
			return new ResponseEntity(i.getId(), HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.IMMUNIZATION_DELETE, LoggerUtil.currentUser(), i.getPatient().getUsername(),
					"Failed to delete immunization");
			return new ResponseEntity(errorResponse("Failed to delete immunization: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Returns a collection of all the immunizationS in the system.
	 *
	 * @return all saved immunizationS
	 */
	@PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_PATIENT')")
	@GetMapping(BASE_PATH + "/immunizations")
	public List<Immunization> getImmunizations() {
		final boolean isHCP = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_HCP"));
		if (isHCP) {
			// Return all immunizations in system
			LoggerUtil.log(TransactionType.IMMUNIZATION_VIEW, LoggerUtil.currentUser(),
					"HCP viewed a list of all immunizations");
			return Immunization.getImmunizations();
		} else {
			// Return only immunizations assigned to the patient
			return Immunization.getForPatient(LoggerUtil.currentUser());
		}
	}

	/**
	 * Returns a single immunization using the given id.
	 *
	 * @param id
	 *            the id of the desired immunization
	 * @return the requested immunization
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@GetMapping(BASE_PATH + "/immunizations/{id}")
	public ResponseEntity getImmunization(@PathVariable final Long id) {
		final Immunization p = Immunization.getById(id);
		if (p == null) {
			LoggerUtil.log(TransactionType.IMMUNIZATION_VIEW, LoggerUtil.currentUser(),
					"Failed to find immunization with id " + id);
			return new ResponseEntity(errorResponse("No immunization found for " + id), HttpStatus.NOT_FOUND);
		} else {
			LoggerUtil.log(TransactionType.IMMUNIZATION_VIEW, LoggerUtil.currentUser(), "Viewed immunization  " + id);
			return new ResponseEntity(p, HttpStatus.OK);
		}
	}

	/**
	 * Returns a collection of all the immunizations in the system.
	 *
	 * @param username
	 *            of the patient
	 * @return all saved immunizations
	 */
	@PreAuthorize("hasAnyRole('ROLE_HCP')")
	@GetMapping(BASE_PATH + "/patientImmunizations/{username}")
	public List<Immunization> getPatientImmunizations(@PathVariable("username") final String username) {
		final boolean isHCP = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_HCP"));
		if (username == null) {
			return null;
		}

		if (isHCP) {
			LoggerUtil.log(TransactionType.IMMUNIZATIONS_VIEW, LoggerUtil.currentUser(), username,
					LoggerUtil.currentUser() + " viewed a patient's immunizations");
		}
		return Immunization.getForPatient(username);
	}

}
