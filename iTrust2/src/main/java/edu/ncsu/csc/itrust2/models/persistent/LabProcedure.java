package edu.ncsu.csc.itrust2.models.persistent;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.criterion.Criterion;

import edu.ncsu.csc.itrust2.forms.hcp.LabProcedureForm;
import edu.ncsu.csc.itrust2.models.enums.Role;

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
    // @NotNull
    @ManyToOne ( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn ( name = "office_visit" )
    OfficeVisit      officeVisit;
    // priority ranking for the procedure (1-4)

    int              priority;
    // code that corresponds to the procedure
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "code" )
    LabProcedureCode code;
    // notes associated with the procedure
    String           notes;
    // the lab tech assigned to the procedure
    String           labtech;
    // the office visit during which the procedure is performed

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
        this.labtech = labtech.getUsername();
        this.officeVisit = officeVisit;
        this.patient = patient;
        this.hcp = hcp;
        this.status = status;
    }

    public LabProcedure ( final LabProcedureForm lpf ) {

        final LabProcedureCode lpc = LabProcedureCode.getById( Long.valueOf( lpf.getCode() ) );
        System.out.println( "\n\n\n\n" + Long.valueOf( lpf.getCode() ) + " " + lpc.getCode() );
        this.setCode( lpc );
        this.setNotes( lpf.getComment() );
        this.setLabtech( lpf.getLabtech() );
        this.setPriority( lpf.getPriority() );
    }

    private void setLabtech ( final String labtech ) {
        this.labtech = labtech;

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
        return User.getByNameAndRole( labtech, Role.ROLE_LABTECH );
    }

    /**
     * sets the lab tech associated with the lab procedure
     *
     * @param labtech
     */
    public void setLabtech ( final User labtech ) {
        this.labtech = labtech.getUsername();
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

    /**
     * Returns the Code with the given ID
     *
     * @param id
     *            The ID to retrieve
     * @return The LabProcedureCode requested if it exists
     */
    public static LabProcedure getById ( final Long id ) {
        try {
            return getWhere( createCriterionAsList( ID, id ) ).get( 0 );
        }
        catch ( final Exception e ) {
            return null;
        }

    }

    /**
     * Returns a List of LabProcedureCode that meet the given WHERE clause
     *
     * @param where
     *            List of Criterion to and together and search for records by
     * @return The list of Codes selected
     */
    @SuppressWarnings ( "unchecked" )
    private static List<LabProcedure> getWhere ( final List<Criterion> where ) {
        return (List<LabProcedure>) getWhere( LabProcedure.class, where );
    }

    /**
     * Returns Lab Procedure's id
     *
     * @return id of the lab procedure
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Return a list of lab procedure for the specified visit
     *
     * @param id
     *            The ID of the Office Visit to search for
     * @return The list of Lab Procedure
     */
    public static List<LabProcedure> getByVisit ( final Long id ) {
        return getWhere( createCriterionAsList( "officeVisit", OfficeVisit.getById( id ) ) );
    }

    /**
     * Returns a list of lab procedures for the specified Patient
     *
     * @param user
     *            The patient to get lab Procedure for
     * @return The list of lab procedure
     */
    public static List<LabProcedure> getForPatient ( final User user ) {
        final List<LabProcedure> labProcedure = new Vector<LabProcedure>();
        OfficeVisit.getForPatient( user.getId() ).stream().map( OfficeVisit::getId )
                .forEach( e -> labProcedure.addAll( getByVisit( e ) ) );
        return labProcedure;

    }
}
