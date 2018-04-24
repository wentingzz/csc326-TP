package edu.ncsu.csc.itrust2.models.persistent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    private Long             id;

    // the office visit during which the procedure is performed
    // @NotNull
    // @ManyToOne
    // @JoinColumn ( name = "office_visit" )
    // private OfficeVisit officeVisit;

    // priority ranking for the procedure (1-4)
    @Min ( 1 )
    @Max ( 4 )
    @JoinColumn ( name = "priority_id" )
    private int              priority;

    // code that corresponds to the procedure
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "code" )
    private LabProcedureCode code;

    // comments associated with the procedure
    @JoinColumn ( name = "comments_id" )
    private String           comments;

    // the lab tech assigned to the procedure
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "labtech_id", columnDefinition = "varchar(100)" )
    private User             labtech;

    // the patient for which the procedure is performed
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "patient_id", columnDefinition = "varchar(100)" )
    private User             patient;

    // the hcp with which the procedure is associated
    /**
     * @NotNull
     * @ManyToOne
     * @JoinColumn ( name = "hcp_id", columnDefinition = "varchar(100)" )
     *             private User hcp;
     */

    // the status of the procedure (success or failure)
    @JoinColumn ( name = "status_id" )
    private String           status;

    /**
     * a constructor for a LabProcedureCode that contains information about how
     * a procedure is preformed during an appointment
     *
     * @param priority
     *            the priority of the procedure, an integer
     * @param code
     *            the LabProcedureCode that accompanies the procedure
     * @param comments
     *            relevant comments regarding the procedure, may be blank
     * @param labtech
     *            the Labtech in charge of the procedure
     * @param officeVisit
     *            the OfficeVisit during which the procedure was performed
     * @param patient
     *            the Patient who underwent the Procedure
     * @param hcp
     *            the HCP that assigned the Procedure
     * @param status
     *            whether the procedure is assigned, in progress, or completed
     */
    public LabProcedure ( final int priority, final LabProcedureCode code, final String comments, final User labtech,
            final OfficeVisit officeVisit, final User patient, final User hcp, final String status ) {
        this.priority = priority;
        this.code = code;
        this.comments = comments;
        this.labtech = labtech;
        // this.officeVisit = officeVisit;
        // this.patient = patient;
        // this.hcp = hcp;
        this.status = status;
    }

    /**
     * Empty constructor for filling in fields without a LabProcedure object.
     */
    public LabProcedure () {
    }

    /**
     * another constructor makes a LabProcedure out of a lab procedure form
     *
     * @param lpf
     *            the form on which to create the procedure
     */
    public LabProcedure ( final LabProcedureForm lpf ) {

        // final LabProcedureCode lpc = lp
        // System.out.println( "\n\n\n\n" + Long.valueOf( lpf.getCode() ) + " "
        // + lpc.getCode() );
        this.setCode( lpf.getCode() );
        this.setComments( lpf.getComment() );
        this.setLabtech( User.getByName( lpf.getLabtech() ) );
        this.setPriority( lpf.getPriority() );
        this.setPatient( User.getByName( lpf.getPatient() ) );
        this.setStatus( "ASSIGNED" );
        // this.setHcp(User.getByName(lpf.getHcp()));
    }

    /**
     * sets the patient associated with this lab procedure
     *
     * @param user
     *            the user that the patient is created from
     */
    public void setPatient ( final User user ) {
        this.patient = user;
    }

    /**
     * returns the priority of the procedure
     *
     * @return priority an int, the priority of the procedure
     */
    public int getPriority () {
        return priority;
    }

    /**
     * sets the priority of the procedure, throws an exception if this is not an
     * integer from 1-4
     *
     * @param priority
     *            the priority to set the procedure as
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
     * @return code the LabProcedureCode associated with the Lab Procedure
     */
    public LabProcedureCode getCode () {
        return code;
    }

    /**
     * sets the lab procedure code
     *
     * @param code
     *            the code to set the procedure as
     */
    public void setCode ( final LabProcedureCode code ) {
        this.code = code;
    }

    /**
     * returns the lab procedure's comments
     *
     * @return comments the comments associated with the procedure
     */
    public String getComments () {
        return comments;
    }

    /**
     * sets the lab procedure's comments
     *
     * @param comments
     *            the comments associated with the procedure
     */
    public void setComments ( final String comments ) {
        if ( comments != null && !comments.equals( "" ) ) {
            this.comments = comments;
        }
    }

    /**
     * returns the lab tech associated with the lab procedure
     *
     * @return labtech the labtech that performs the procedure
     */
    public User getLabtech () {
        return labtech;
    }

    /**
     * sets the lab tech associated with the lab procedure
     *
     * @param labtech
     *            the labtech to be associated with the procedure
     */
    public void setLabtech ( final User labtech ) {
        this.labtech = labtech;
    }

    // /**
    // * returns the office visit associated with this lab procedure
    // *
    // * @return
    // */
    // public OfficeVisit getOfficevisit () {
    // return officeVisit;
    // }
    //
    // /**
    // * sets the office visit associated with this lab procedure
    // *
    // * @param officevisit
    // */
    // public void setOfficevisit ( final OfficeVisit officevisit ) {
    // this.officeVisit = officevisit;
    // }

    /**
     * gets the Patient associated with the lab procedure
     *
     * @return patient the patient associated with the procedure
     */
    public User getPatient () {
        return patient;
    }

    /**
     * returns the HCP associated with the lab procedure
     *
     * @return
     */
    /**
     * public User getHcp () { return hcp; }
     */

    /**
     * sets the HCP associated with the lab procedure
     *
     * @param hcp
     */
    /**
     * public void setHcp ( final User hcp ) { this.hcp = hcp; }
     */

    /**
     * gets the status of the lab procedure
     *
     * @return status the status associated with the procedure
     */
    public String getStatus () {
        return status;
    }

    /**
     * sets the status of the lab procedure
     *
     * @param status
     *            the status associated with the procedure
     */
    public void setStatus ( final String status ) {
        this.status = status;
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

    public static List<LabProcedure> getByLabTech ( final String labtech ) {
        final List<LabProcedure> all = getAll();
        final int size = all.size();
        final ArrayList<LabProcedure> result = new ArrayList<LabProcedure>();
        for ( int i = 0; i < size; i++ ) {
            if ( all.get( i ).getLabtech().getUsername().equals( labtech ) ) {
                result.add( all.get( i ) );
            }
        }
        return result;
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
    // public static List<LabProcedure> getByVisit ( final Long id ) {
    // return getWhere( createCriterionAsList( "officeVisit",
    // OfficeVisit.getById( id ) ) );
    // }

    /**
     * Returns a list of lab procedures for the specified Patient
     *
     * @param patient
     *            The patient to get lab Procedure for
     * @return The list of lab procedure
     */
    public static List<LabProcedure> getForPatient ( final String patient ) {
        // final List<LabProcedure> labProcedure = new Vector<LabProcedure>();
        // OfficeVisit.getForPatient( user.getId() ).stream().map(
        // OfficeVisit::getId )
        // .forEach( e -> labProcedure.addAll( getByVisit( e ) ) );
        // return labProcedure;
        return getWhere( createCriterionAsList( "patient", User.getByNameAndRole( patient, Role.ROLE_PATIENT ) ) );
    }

    /**
     * returns all lab procedures associated with an office visit
     * 
     * @param id
     *            the id of the office visit
     * @return getByOfficeVisit a list of procedures associated with the office
     *         visit
     */
    public static List<LabProcedure> getByOfficeVisit ( final Long id ) {
        return getWhere( createCriterionAsList( "labProcedure_id", id ) );
    }
}
