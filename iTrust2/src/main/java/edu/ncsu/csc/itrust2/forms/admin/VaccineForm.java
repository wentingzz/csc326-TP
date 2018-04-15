package edu.ncsu.csc.itrust2.forms.admin;

import edu.ncsu.csc.itrust2.models.persistent.Vaccine;

/**
 * A form for REST API communication. Contains fields for constructing a Vaccine
 * objects.
 *
 * @author Abeer Altaf
 */
public class VaccineForm {

	/** Unique id for a vaccine */
	private Long id;

	/** The name of the vaccine */
	private String name;

	/** The CPT Code of the vaccine */
	private String code;

	/** Description of the vaccine */
	private String description;

	/**
	 * Empty constructor for filling in fields without a Vaccine object
	 */
	public VaccineForm() {

	}

	/**
	 * Constructs a new form with information from the given vaccine.
	 * 
	 * @param vaccine
	 *            the Vaccine object
	 */
	public VaccineForm(final Vaccine vaccine) {
		setId(vaccine.getId());
		setName(vaccine.getName());
		setCode(vaccine.getCode());
		setDescription(vaccine.getDescription());
	}

	/**
	 * Returns the vaccine's id
	 * 
	 * @return id the vaccine's id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the vaccine's id to the given value
	 * 
	 * @param id
	 *            the vaccine's id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the name of the vaccine
	 * 
	 * @return name the name of the vaccine
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the vaccine to the given value
	 * 
	 * @param name
	 *            the name of the vaccine
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the CPT code of the vaccine
	 * 
	 * @return code the CPT code of the vaccine
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the CPT code to the given value
	 * 
	 * @param code
	 *            the CPT code for the vaccine
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the vaccine's description
	 * 
	 * @return description the vaccine's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description to the given value
	 * 
	 * @param description
	 *            the description of the vaccine
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
