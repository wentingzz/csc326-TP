package edu.ncsu.csc.itrust2.models.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.ncsu.csc.itrust2.forms.admin.LabProcedureCodeForm;

/**
 * represents the lab procedure codes that a lab procedure will have; these will
 * show up on Office Visit forms to represent a Lab Procedure
 *
 * @author Hannah Morrison (hmorris3)
 *
 */
@Entity
@Table ( name = "LabProcedureCodes" )
public class LabProcedureCode extends DomainObject<LabProcedureCode> {

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long   id;

    private String code;
    private String component;
    private String property;
    private String longCommonName;
    private Date   dateCreated;

    /**
     * Empty constructor for Hibernate.
     */
    public LabProcedureCode () {
    }

    /**
     * Construct a new Prescription using the details in the given form.
     *
     * @param form
     *            the prescription form
     */
    public LabProcedureCode ( final LabProcedureCodeForm form ) {
        setCode( form.getCode() );
        setComponent( form.getComponent() );
        setProperty( form.getProperty() );
        setLongCommonName( form.getLongCommonName() );
        setDateCreated( form.getDateCreated() );
    }

    /**
     * from LOINC documentation: The component/analyte consists of three main
     * subparts: The principal name (e.g., the name of the analyte or the
     * measurement); challenge of provocation, if relevant, including time
     * delay, substance of challenge, amount administered, and route of
     * administration; and any standardization or adjustment.
     *
     * @return component, the LOINC component of the code
     */
    public String getComponent () {
        return component;
    }

    /**
     * sets the component
     *
     * @param component
     *            what to set the LabProcedureCode's component as
     */
    public void setComponent ( final String component ) {
        this.component = component;
    }

    /**
     * from the LOINC documentation: The kind of property distinguishes between
     * different kinds of quantities of the same substance. Analytes are often
     * measured using different types of units. Kinds of properties include:
     * Mass, Substance, Catalytic Activity, Arbitrary, and Number.
     * Pharmaceutical industry terms for tests include properties, such as Mass
     * Substance Concentration (MSCnc) or Mass Substance Rate (MSRat).
     *
     * @return property the property of the Lab Procedure
     */
    public String getProperty () {
        return property;
    }

    /**
     * sets the property of the lab procedure
     *
     * @param property
     */
    public void setProperty ( final String property ) {
        this.property = property;
    }

    /**
     * gets the long common name
     *
     * @return longCommonName the commonly used name of the procedure
     */
    public String getLongCommonName () {
        return longCommonName;
    }

    /**
     * sets the name of the procedure
     *
     * @param longCommonName
     *            name of the procedure
     */
    public void setLongCommonName ( final String longCommonName ) {
        this.longCommonName = longCommonName;
    }

    /**
     * gets the date the procedure was created in the system
     *
     * @return dateCreated the date the procedure was created by an iTrust admin
     */
    public Date getDateCreated () {
        return dateCreated;
    }

    /**
     * sets the date the lab procedure was created
     *
     * @param dateCreated
     *            the date the procedure was created by an iTrust admin
     */
    public void setDateCreated ( final Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    /**
     * sets the code for the lab procedure Throws an exception if the code
     * contains a special character Pattern matching code taken from:
     * https://stackoverflow.com/questions/1795402/java-check-a-string-if-there-is-a-special-character-in-it?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     * @param code
     *            the code to set
     */
    public void setCode ( final String code ) {
        final Pattern p = Pattern.compile( "[^a-z0-9 ]" );
        final Matcher m = p.matcher( code );
        final boolean b = m.find();
        if ( b ) {
            throw new IllegalArgumentException( "The LOINC code may only contain lowercase letters and numerals 0-9" );
        }
        this.code = code;
    }

    /**
     * returns the code for the lab procedure code
     *
     * @return code the code for the lab procedure
     */
    public String getCode () {
        return code;
    }

    /**
     * gets the ID mapped to the table
     */
    @Override
    public Serializable getId () {
        return id;
    }

    /**
     * sets the ID
     *
     * @param id
     *            mapped to the table
     */
    public void setId ( final long id ) {
        this.id = id;
    }

    /**
     * Gets a collection of all the prescriptions in the system.
     *
     * @return the system's prescription
     */
    @SuppressWarnings ( "unchecked" )
    public static List<LabProcedureCode> getAll () {
        return (List<LabProcedureCode>) DomainObject.getAll( LabProcedureCode.class );
    }

}
