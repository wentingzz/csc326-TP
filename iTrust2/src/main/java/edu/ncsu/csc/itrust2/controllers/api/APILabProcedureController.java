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

import edu.ncsu.csc.itrust2.forms.hcp.LabProcedureForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Controller responsible for providing various REST API endpoints for the
 * Personnel model.
 *
 * @author Azra Shaikh
 *
 */
@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class APILabProcedureController extends APIController {

	/**
	 * Retrieves and returns a list of all Lab Procedures.
	 *
	 * of the visit
	 *
	 * @return list of lab procedures
	 */
	@PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_PATIENT')")
	@GetMapping(BASE_PATH + "/labProcedures")
	public List<LabProcedure> getLabProcedures() {
		final boolean isHCP = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_HCP"));
		if (isHCP) {
			// Return all lab procedures in system
			LoggerUtil.log(TransactionType.VIEW_LAB_PROCEDURE, LoggerUtil.currentUser(),
					"HCP viewed a list of all lab procedures");
			return LabProcedure.getAll();
		} else {
			// Return only lab procedures assigned to the patient
			return LabProcedure.getForPatient(LoggerUtil.currentUser());
		}
	}

	/**
	 * Retrieves and returns a list of all Lab Procedures for a user. Used when a
	 * LabTech views the lab procedures assigned to them
	 *
	 * @return list of lab procedures
	 */
	@GetMapping(BASE_PATH + "/labtech/labprocedures/")
	@PreAuthorize("hasRole('ROLE_LABTECH')")
	public List<LabProcedure> getLabTechProcedures() {
		return LabProcedure.getByLabTech(LoggerUtil.currentUser());
	}

	/**
	 * Retrieves and returns a list of all Lab Procedures for a user. Used when a
	 * office visit
	 *
	 * @return list of lab procedures
	 */
	// @GetMapping ( BASE_PATH + "/labprocedures/{id}" )
	// @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
	// public List<LabProcedure> getLabProceduresByVisit ( @PathVariable ( "id"
	// ) final String id ) {
	// final Long realId = Long.valueOf( id );
	// return LabProcedure.getByOfficeVisit( realId );
	// }

	/**
	 * Returns a list of lab techs
	 *
	 * @return a list of lab techs
	 */
	@GetMapping(BASE_PATH + "/labtechs/")
	@PreAuthorize("hasRole('ROLE_LABTECH') OR hasRole('ROLE_HCP')")
	public List<User> getLabTechs() {
		return User.getLabtechs();
	}

	/**
	 * Adds a lab procedure with the given id
	 * 
	 * @param id
	 *            the id of the lab procedure
	 * @param form
	 *            the lab procedure form
	 * @return response entity
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@PostMapping(BASE_PATH + "/labProcedures/{id}")
	public ResponseEntity addLabProcedure(@PathVariable("id") final String id,
			@RequestBody final LabProcedureForm form) {
		try {
			final Long realId = Long.valueOf(id);
			final OfficeVisit ov = OfficeVisit.getById(realId);

			final LabProcedure lp = new LabProcedure(form);
			ov.addLabProcedure(lp);
			LoggerUtil.log(TransactionType.CREATE_LAB_PROCEDURE, LoggerUtil.currentUser(),
					lp.getPatient().getUsername(), "Created lab procedure with id " + lp.getId());
			return new ResponseEntity(lp, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.CREATE_LAB_PROCEDURE, LoggerUtil.currentUser(),
					"Failed to create lab procedure");
			return new ResponseEntity(errorResponse("Could not save the lab procedure: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Updates the Lab Procedure with the id provided by overwriting it with the new
	 * Personnel record that is provided. If the ID provided does not match the ID
	 * set in the Patient provided, the update will not take place
	 * 
	 * @param id
	 *            the id of the Lab procedure form
	 * @param status
	 *            the status of the Lab procedure
	 * @param labtech
	 *            the labtech of the Lab procedure
	 * @return resonse entity
	 */
	@PutMapping(BASE_PATH + "/labprocedure/{id}/{status}/{labtech}/")
	@PreAuthorize("hasRole('ROLE_LABTECH')")
	public ResponseEntity updateLabProcedure(@PathVariable("id") final String id,
			@PathVariable("status") final String status, @PathVariable("labtech") final String labtech) {
		try {
			final Long realId = Long.valueOf(id);
			final LabProcedure procedure = LabProcedure.getById(realId);
			if (procedure == null) {
				return new ResponseEntity("No code with id " + id, HttpStatus.NOT_FOUND);
			}
			if (procedure.getStatus().equals("COMPLETE") && status.equals("IN_PROGRESS")) {
				return new ResponseEntity("Can not set the status to \"IN_PROGRESS\" after \"COMPLETE\" for " + id,
						HttpStatus.BAD_REQUEST);
			}
			procedure.setStatus(status);
			procedure.setLabtech(User.getByName(labtech));
			procedure.save();
			final String username = LoggerUtil.currentUser();
			// try {
			// uSer = User.getByName(
			// SecurityContextHolder.getContext().getAuthentication().getName()
			// );
			// }
			// catch ( final Exception e ) {
			// // ignore, its was a test that wasn't authenticated properly.
			// }
			LoggerUtil.log(TransactionType.EIDT_LAB_PROCEDURE, username, username + " edited an Lab Procedure Code");

			return new ResponseEntity(procedure, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity(
					errorResponse("Could not update Lab Procedure " + id + " because of " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Updates the Lab Procedure with the id provided by overwriting it with the new
	 * Personnel record that is provided. If the ID provided does not match the ID
	 * set in the Patient provided, the update will not take place
	 * 
	 * @param id
	 *            the id of the lab procedure
	 * @param status
	 *            the status of the lab procedure
	 * @param labtech
	 *            the labtech of the lab procedure
	 * @param comments
	 *            the commments of the lab procedure
	 * @return response entity
	 */
	@PutMapping(BASE_PATH + "/labprocedure/{id}/{status}/{labtech}/{comments}")
	@PreAuthorize("hasRole('ROLE_LABTECH')")
	public ResponseEntity updateLabProcedureWithComments(@PathVariable("id") final String id,
			@PathVariable("status") final String status, @PathVariable("labtech") final String labtech,
			@PathVariable("comments") final String comments) {
		try {
			final Long realId = Long.valueOf(id);
			final LabProcedure procedure = LabProcedure.getById(realId);
			if (procedure == null) {
				return new ResponseEntity("No code with id " + id, HttpStatus.NOT_FOUND);
			}
			if (procedure.getStatus().equals("COMPLETE") && status.equals("IN_PROGRESS")) {
				return new ResponseEntity("Can not set the status to \"IN_PROGRESS\" after \"COMPLETE\" for " + id,
						HttpStatus.BAD_REQUEST);
			}
			procedure.setStatus(status);
			procedure.setLabtech(User.getByName(labtech));
			procedure.setComments(comments);
			procedure.save();
			final String username = LoggerUtil.currentUser();
			// try {
			// uSer = User.getByName(
			// SecurityContextHolder.getContext().getAuthentication().getName()
			// );
			// }
			// catch ( final Exception e ) {
			// // ignore, its was a test that wasn't authenticated properly.
			// }
			LoggerUtil.log(TransactionType.EIDT_LAB_PROCEDURE, username, username + " edited an Lab Procedure Code");

			return new ResponseEntity(procedure, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity(
					errorResponse("Could not update Lab Procedure " + id + " because of " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Edits an existing lab procedure in the system. Matches lab procedure by ids.
	 * Requires HCP permissions.
	 *
	 * @param form
	 *            the form containing the details of the new lab procedure
	 * @return the edited lab procedure
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@PutMapping(BASE_PATH + "/labProcedures")
	public ResponseEntity editLabProcedure(@RequestBody final LabProcedureForm form) {
		try {
			final LabProcedure lp = new LabProcedure(form);
			final LabProcedure saved = LabProcedure.getById(lp.getId());
			if (saved == null) {
				LoggerUtil.log(TransactionType.EIDT_LAB_PROCEDURE, LoggerUtil.currentUser(),
						"No lab procedure found with id " + lp.getId());
				return new ResponseEntity(errorResponse("No lab procedure found with id " + lp.getId()),
						HttpStatus.NOT_FOUND);
			}
			lp.save(); /* Overwrite existing */
			LoggerUtil.log(TransactionType.EIDT_LAB_PROCEDURE, LoggerUtil.currentUser(), lp.getPatient().getUsername(),
					"Edited lab procedure with id " + lp.getId());
			return new ResponseEntity(lp, HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.EIDT_LAB_PROCEDURE, LoggerUtil.currentUser(),
					"Failed to edit lab procedure");
			return new ResponseEntity(errorResponse("Failed to update lab procedure: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes an existing Lab Procedure record
	 * 
	 * @param id
	 *            the id of the lab procedure
	 * @return response entity
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@DeleteMapping(BASE_PATH + "/labprocedures/{id}")
	public ResponseEntity deleteLabProcedure(@PathVariable final Long id) {
		final LabProcedure lp = LabProcedure.getById(id);
		if (lp == null) {
			return new ResponseEntity(errorResponse("No labProcedure found with id " + id), HttpStatus.NOT_FOUND);
		}
		try {
			lp.delete();
			LoggerUtil.log(TransactionType.Delete_LAB_PROCEDURE, LoggerUtil.currentUser(),
					lp.getPatient().getUsername(), "Deleted lab procedure with id " + lp.getId());
			return new ResponseEntity(lp.getId(), HttpStatus.OK);
		} catch (final Exception e) {
			LoggerUtil.log(TransactionType.Delete_LAB_PROCEDURE, LoggerUtil.currentUser(),
					lp.getPatient().getUsername(), "Failed to delete lab procedure");
			return new ResponseEntity(errorResponse("Failed to delete lab procedure: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Returns a single lab procedure using the given id.
	 *
	 * @param id
	 *            the id of the desired lab procedure
	 * @return the requested lab procedure
	 */
	@PreAuthorize("hasRole('ROLE_HCP')")
	@GetMapping(BASE_PATH + "/labProcedures/{id}")
	public ResponseEntity getLabProcedure(@PathVariable("id") final Long id) {
		final LabProcedure lp = LabProcedure.getById(id);
		if (lp == null) {
			LoggerUtil.log(TransactionType.VIEW_LAB_PROCEDURE, LoggerUtil.currentUser(),
					"Failed to find lab procedure with id " + id);
			return new ResponseEntity(errorResponse("No lab procedure found for " + id), HttpStatus.NOT_FOUND);
		} else {
			LoggerUtil.log(TransactionType.VIEW_LAB_PROCEDURE, LoggerUtil.currentUser(), "Viewed lab procedure  " + id);
			return new ResponseEntity(lp, HttpStatus.OK);
		}
	}
}
