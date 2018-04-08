package edu.ncsu.csc.itrust2.models.persistent;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents a lab procedure that a patient can have
 *
 * @author Hannah
 *
 */
@Entity
@Table ( name = "LabProcedure" )
public class LabProcedure extends DomainObject<LabProcedure> {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long     id;
    // priority ranking for the procedure (1-4)
    int              priority;
    // code that corresponds to the procedure
    LabProcedureCode code;
    // notes associated with the procedure
    String           notes;
    // the lab tech assigned to the procedure
    User             labtech;
    // the office visit during which the procedure is performed
    OfficeVisit      officeVisit;
    // the patient for which the procedure is performed
    User             patient;
    // the hcp with which the procedure is associated
    User             hcp;
    // the status of the procedure (success or failure)
    String           status;

    /**
     * constructor
     */
    public LabProcedure ( final int priority, final LabProcedureCode code, final String notes, final User labtech,
            final OfficeVisit officeVisit, final User patient, final User hcp, final String status ) {
        this.priority = priority;
        this.code = code;
        this.notes = notes;
        this.labtech = labtech;
        this.officeVisit = officeVisit;
        this.patient = patient;
        this.hcp = hcp;
        this.status = status;
    }

    /**
     * returns the priority of the procedure
     *
     * @return
     */
    public int getPriority () {
        return priority;
    }

    /**
     * sets the priority of the procedure, throws an exception if this is not an
     * integer from 1-4
     *
     * @param priority
     */
    public void setPriority ( final int priority ) {
        if ( ( priority < 1 ) || ( priority > 4 ) ) {
            throw new IllegalArgumentException( "Priority must be a number between 1 and 4" );
        }
        this.priority = priority;
    }

    /**
     * returns the code associated with the procedure
     *
     * @return
     */
    public LabProcedureCode getCode () {
        return code;
    }

    /**
     * sets the lab procedure code
     *
     * @param code
     */
    public void setCode ( final LabProcedureCode code ) {
        this.code = code;
    }

    /**
     * returns the lab procedure's notes
     *
     * @return
     */
    public String getNotes () {
        return notes;
    }

    /**
     * sets the lab procedure's notes
     *
     * @param notes
     */
    public void setNotes ( final String notes ) {
        this.notes = notes;
    }

    /**
     * returns the lab tech associated with the lab procedure
     *
     * @return
     */
    public User getLabtech () {
        return labtech;
    }

    /**
     * sets the lab tech associated with the lab procedure
     *
     * @param labtech
     */
    public void setLabtech ( final User labtech ) {
        this.labtech = labtech;
    }

    /**
     * returns the office visit associated with this lab procedure
     *
     * @return
     */
    public OfficeVisit getOfficevisit () {
        return officeVisit;
    }

    /**
     * sets the office visit associated with this lab procedure
     *
     * @param officevisit
     */
    public void setOfficevisit ( final OfficeVisit officevisit ) {
        this.officeVisit = officevisit;
    }

    /**
     * gets the Patient associated with the lab procedure
     *
     * @return
     */
    public User getPatient () {
        return patient;
    }

    /**
     * sets the patient associated with this lab procedure
     *
     * @param patient
     */
    public void setPatient ( final User patient ) {
        this.patient = patient;
    }

    /**
     * returns the HCP associated with the lab procedure
     *
     * @return
     */
    public User getHcp () {
        return hcp;
    }

    /**
     * sets the HCP associated with the lab procedure
     *
     * @param hcp
     */
    public void setHcp ( final User hcp ) {
        this.hcp = hcp;
    }

    /**
     * gets the status of the lab procedure
     *
     * @return
     */
    public String getStatus () {
        return status;
    }

    /**
     * sets the status of the lab procedure
     *
     * @param status
     */
    public void setStatus ( final String status ) {
        // if the status is some variation of "in-progress," "in progress," or
        // "completed" then it is valid; otherwise, it is not a valid status
        if ( ( status.toLowerCase().contains( "in" ) && status.toLowerCase().contains( "progress" ) )
                || status.toLowerCase().contains( "complete" ) ) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException( "Status must be 'in progress' or 'complete'" );
        }
    }

    /**
     * Gets a collection of all the prescriptions in the system.
     *
     * @return the system's prescription
     */
    @SuppressWarnings ( "unchecked" )
    public static List<LabProcedure> getAll () {
        return (List<LabProcedure>) DomainObject.getAll( LabProcedure.class );
    }

    @Override
    public Serializable getId () {
        // TODO Auto-generated method stub
        return id;
    }
}
