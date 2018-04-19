package edu.ncsu.csc.itrust2.models.persistent;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;

import edu.ncsu.csc.itrust2.forms.hcp.ImmunizationForm;
import edu.ncsu.csc.itrust2.models.enums.Role;

/**
 * Represents a immunization in the system. Each immunization is associated with
 * a patient and a vaccine.
 *
 * @author Abeer Altaf
 * @author Apeska Awasthi
 */
@Entity
@Table(name = "Immunizations")
public class Immunization extends DomainObject<Immunization> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "vaccine_id")
	private Vaccine vaccine;
	

	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_id", columnDefinition = "varchar(100)")
	private User patient;

	/**
	 * Empty constructor for Hibernate.
	 */
	public Immunization() {
	}

	/**
	 * Construct a new Immunization using the details in the given form.
	 *
	 * @param form
	 *            the immunization form
	 */
	public Immunization(final ImmunizationForm form) {
		setVaccine(Vaccine.getByCode(form.getVaccine()));
		setPatient(User.getByName(form.getPatient()));

		if (form.getId() != null) {
			setId(form.getId());
		}
	}

	/**
	 * Sets the Immunization's unique id.
	 *
	 * @param id
	 *            the immunization id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Returns the id associated with the Immunization.
	 *
	 * @return the immunization id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Returns the vaccine associated with this Immunization
	 *
	 * @return the immunization's vaccine
	 */
	public Vaccine getVaccine() {
		return vaccine;
	}

	/**
	 * Associates this immunization with the given vaccine.
	 *
	 * @param vaccine
	 *            the vaccine
	 */
	public void setVaccine(final Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	/**
	 * Returns the user associated with this immunization.
	 *
	 * @return the patient
	 */
	public User getPatient() {
		return patient;
	}

	/**
	 * Sets the immunization's patient to the given user
	 *
	 * @param user
	 *            the user
	 */
	public void setPatient(final User user) {
		this.patient = user;
	}

	/**
	 * Rerieve all Immunizations for the patient provided
	 * 
	 * @param patient
	 *            The Patient to find Immunizations for
	 * @return The List of records that was found
	 */
	public static List<Immunization> getForPatient(final String patient) {
		return getWhere(createCriterionAsList("patient", User.getByNameAndRole(patient, Role.ROLE_PATIENT)));
	}

	/**
	 * Returns a collection of immunizations that meet the "where" query
	 *
	 * @param where
	 *            List of Criterion to and together and search for records by
	 * @return a collection of matching immunizations
	 */
	@SuppressWarnings("unchecked")
	private static List<Immunization> getWhere(final List<Criterion> where) {
		return (List<Immunization>) getWhere(Immunization.class, where);
	}

	/**
	 * Gets the Immunization with the given id, or null if none exists.
	 *
	 * @param id
	 *            the id to query for
	 * @return the matching immunization
	 */
	public static Immunization getById(final Long id) {
		try {
			return getWhere(createCriterionAsList(ID, id)).get(0);
		} catch (final ObjectNotFoundException e) {
			return null;
		}
	}

	/**
	 * Gets a collection of all the immunizations in the system.
	 *
	 * @return the system's immunization
	 */
	@SuppressWarnings("unchecked")
	public static List<Immunization> getImmunizations() {
		return (List<Immunization>) DomainObject.getAll(Immunization.class);
	}

}
