package edu.ncsu.csc.itrust2.forms.admin;

import java.io.Serializable;
import java.util.Date;

import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;

/**
 * A form for REST API communication. Contains fields for constructing
 * LabProcedureCode objects.
 *
 */
public class LabProcedureCodeForm {
    private Serializable id;
    private String       code;
    private String       component;
    private String       property;
    private String       longCommonName;
    private Date         dateCreated;

    /**
     * Empty constructor for filling in fields without a LabProcedureCode
     * object.
     */
    public LabProcedureCodeForm () {
    }

    /**
     * Constructs a new form with information from the given prescription.
     *
     * @param prescription
     *            the prescription object
     */
    public LabProcedureCodeForm ( final LabProcedureCode labProcedureCode ) {
        setId( labProcedureCode.getId() );
        setComponent( labProcedureCode.getComponent() );
        setProperty( labProcedureCode.getProperty() );
        setLongCommonName( labProcedureCode.getLongCommonName() );
        setDateCreated( labProcedureCode.getDateCreated() );
    }

    /**
     * sets the date
     *
     * @param date
     *            date form is created
     */
    private void setDateCreated ( final Date date ) {
        dateCreated = date;

    }

    /**
     * sets the long common name of the lab procedure
     *
     * @param longCommonName
     *            name of the procedure
     */
    private void setLongCommonName ( final String longCommonName ) {
        this.longCommonName = longCommonName;

    }

    /**
     * sets the property attribute of the procedure
     *
     * @param property
     *            property attribute of the lab procedure
     */
    private void setProperty ( final String property ) {
        this.property = property;

    }

    /**
     * sets the component attribute of the lab procedure
     *
     * @param component
     *            component attribute of the procedure
     */
    private void setComponent ( final String component ) {
        this.component = component;

    }

    /**
     * sets the id of the procedure
     *
     * @param id
     */
    private void setId ( final Serializable id ) {
        this.id = id;

    }

    /**
     * gets the code attribute of the LabProcedureCode
     *
     * @return
     */
    public String getCode () {
        return code;
    }

    /**
     * returns the component attribute of the procedure
     *
     * @return component the component attribute of the procedure
     */
    public String getComponent () {
        return component;
    }

    /**
     * returns the property attribute of the procedure
     *
     * @return property the property of the procedure
     */
    public String getProperty () {
        return property;
    }

    /**
     * returns the name of the procedure
     *
     * @return
     */
    public String getLongCommonName () {
        return longCommonName;
    }

    /**
     * gets the date of the procedure
     *
     * @return dateCreated date of the procedure
     */
    public Date getDateCreated () {
        return dateCreated;
    }

}
