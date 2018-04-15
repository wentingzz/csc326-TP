package edu.ncsu.csc.itrust2.models.persistent;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.criterion.Criterion;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import edu.ncsu.csc.itrust2.forms.admin.VaccineForm;

/**
 * Represents a vaccine
 *
 * @author Abeer Altaf
 */
@Entity
@Table(name = "Vaccines")
public class Vaccine extends DomainObject<Vaccine> {

	/** For Hibernate/Thymeleaf _must_ be an empty constructor */
	public Vaccine() {
	}

	/**
	 * Constructs a new form from the details in the given form
	 * 
	 * @param form
	 *            the form to base the new vaccine on
	 */
	public Vaccine(final VaccineForm form) {
		setId(form.getId());
		setCode(form.getCode());
		setName(form.getName());
		setDescription(form.getDescription());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Pattern(regexp = "^\\d{5}$")
	private String code;

	@NotEmpty
	@Length(max = 64)
	private String name;

	@NotNull
	@Length(max = 1024)
	private String description;

	/**
	 * Returns the id associated with this vaccine
	 * 
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Returns the CPT code of the vaccine
	 * 
	 * @return the CPT code of the vaccine
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the CPT code of the vaccine to the given value
	 * 
	 * @param code
	 *            the CPT code of the vaccine
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the name of the vaccine
	 * 
	 * @return the name of the vaccine
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
	 * Returns the description of the vaccine
	 * 
	 * @return the description of the vaccine
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the vaccine to the given value
	 * 
	 * @param description
	 *            the description of the vaccine
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the id of the vaccine to the given value
	 * 
	 * @param id
	 *            the id of the vaccine
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets a list of vaccines that match the given query.
	 *
	 * @param where
	 *            List of Criterion to and together and search for records by
	 * @return the collection of matching vaccines
	 */
	@SuppressWarnings("unchecked")
	private static List<Vaccine> getWhere(final List<Criterion> where) {
		return (List<Vaccine>) getWhere(Vaccine.class, where);
	}

	/**
	 * Returns the vaccine whose id matches the given value.
	 *
	 * @param id
	 *            the id to search for
	 * @return the matching vaccine or null if none is found
	 */
	public static Vaccine getById(final Long id) {
		try {
			return getWhere(createCriterionAsList(ID, id)).get(0);
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * Gets the vaccine with the code matching the given value. Returns null if none
	 * found.
	 *
	 * @param code
	 *            the code to search for
	 * @return the matching vaccine
	 */
	public static Vaccine getByCode(final String code) {
		try {
			return getWhere(createCriterionAsList("code", code)).get(0);
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * Collects and returns all vaccines in the system
	 *
	 * @return all saved vaccines
	 */
	@SuppressWarnings("unchecked")
	public static List<Vaccine> getAll() {
		return (List<Vaccine>) DomainObject.getAll(Vaccine.class);
	}

}
