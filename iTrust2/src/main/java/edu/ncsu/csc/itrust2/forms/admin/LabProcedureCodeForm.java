package edu.ncsu.csc.itrust2.forms.admin;

import java.util.Date;

import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;

/**
 * Intermediate form for adding or editing Lab Procedrure Codes. Used to create
 * and serialize Lab Procedure Codes.
 *
 * @author Azra Shaikh and Hannah Morrison (hmorris3)
 *
 */
public class LabProcedureCodeForm {

    /** The code of the Lab Procedure */
    private String code;

    /** The component of the Lab Procedure */
    private String component;

    /** The property of the Lab Procedure */
    private String property;

    /** The Lonf Common Name of the Lab Procedure */
    private String longCommonName;

    /** The creation date of the Lab Procedure */
    private Date   dateCreated;

    /** The id of the Lab Procedure */
    private Long   id;

    /**
     * Empty constructor for GSON
     */
    public LabProcedureCodeForm () {

    }

    /**
     * Construct a form off an existing Lab Procedure Code object
     *
     * @param code
     *            The object to fill this form with
     */
    public LabProcedureCodeForm ( final LabProcedureCode code ) {
        setCode( code.getCode() );
        setComponent( code.getComponent() );
        setProperty( code.getProperty() );
        setLongCommonName( code.getLongCommonName() );
        setId( code.getId() );
    }

    /**
     * Returns the String representation of the code
     *
     * @return the code The new code
     */
    public String getCode () {
        return code;
    }

    /**
     * Sets the String representation of the code
     *
     * @param code
     *            the code to set
     */
    public void setCode ( String code ) {
        this.code = code;
    }

    /**
     * Returns the component of this code
     *
     * @return the component of the code
     */
    public String getComponent () {
        return component;
    }

    /**
     * Sets the component of the code
     *
     * @param component
     *            the component to set
     */
    public void setComponent ( String component ) {
        this.component = component;
    }

    /**
     * Returns the property of this code
     *
     * @return the property of the code
     *
     */
    public String getProperty () {
        return property;
    }

    /**
     * Sets the property of the code
     *
     * @param property
     *            the property to set
     */
    public void setProperty ( String property ) {
        this.property = property;
    }

    /**
     * Returns the long common name of this code
     *
     * @return the longCommonName
     */
    public String getLongCommonName () {
        return longCommonName;
    }

    /**
     * Sets the long common name of the code
     *
     * @param longCommonName
     *            the longCommonName to set
     */
    public void setLongCommonName ( String longCommonName ) {
        this.longCommonName = longCommonName;
    }

    /**
     * Returns the date created of this code
     *
     * @return the dateCreated
     */
    public Date getDateCreated () {
        return dateCreated;
    }

    /**
     * Sets the date created of the code
     *
     * @param dateCreated
     *            the dateCreated to set
     */
    public void setDateCreated ( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the id of this code
     *
     * @return the id
     */
    public Long getId () {
        return id;
    }

    /**
     * Sets the id of the code
     *
     * @param id
     *            the id to set
     */
    public void setId ( Long id ) {
        this.id = id;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( o instanceof LabProcedureCodeForm ) {
            final LabProcedureCodeForm f = (LabProcedureCodeForm) o;
            return code.equals( f.getCode() ) && id.equals( f.getId() ) && component.equals( f.getComponent() )
                    && property.equals( f.getProperty() ) && longCommonName.equals( f.getLongCommonName() )
                    && dateCreated.equals( f.getDateCreated() );
        }
        return false;
    }

}
