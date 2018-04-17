package edu.ncsu.csc.itrust2.forms.hcp;

import java.io.Serializable;

import edu.ncsu.csc.itrust2.models.persistent.Immunization;

/**
 * A form for REST API communication. Contains fields for constructing
 * Immunization objects.
 *
 * @author Abeer Altaf
 * @author Apeksha Awasthi
 */
public class ImmunizationForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String vaccine;
	private String patient;
	private Long id;
	
	/**
	 * Empty constructor for filling in fields without an Immunization object
	 */
	public ImmunizationForm() {
		
	}
	
	/**
	 * Constructs a new form with information from the given immunization
	 * @param immunization the immunization object
	 */
	public ImmunizationForm (Immunization immunization) {
		setId(immunization.getId());
		setVaccine(immunization.getVaccine().getCode());
		setPatient(immunization.getPatient().getUsername());
	}
	
	/**
	 * Returns the vaccine associated with this Immunization
	 * @return the immunization's vaccine
	 */
	public String getVaccine() {
		return vaccine;
	}
	
	/**
	 * Associates this immunization with the given vaccine
	 * @param vaccine the vaccine
	 */
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	
	/**
	 * Returns the user associated with this immunization
	 * @return the patient associated with this immunization
	 */
	public String getPatient() {
		return patient;
	}
	
	/**
     * Sets the immunization's patient to the given user
     *
     * @param patient
     *            the patient
     */
	public void setPatient(String patient) {
		this.patient = patient;
	}
	
	/**
     * Returns the id associated with the Immunization.
     *
     * @return the immunization id
     */
	public Long getId() {
		return id;
	}
	
	/**
     * Sets the Immunization's unique id.
     *
     * @param id
     *            the immunization id
     */
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
