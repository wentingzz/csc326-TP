package edu.ncsu.csc.itrust2.forms.hcp;

import java.io.Serializable;

import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;

/**
 * A form for REST API communication. Contains fields for constructing
 * LabProcedure objects.
 *
 * @author Azra
 * @author Abeer
 * @author Apeksha
 *
 */
public class LabProcedureForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private LabProcedureCode  code;
    private int               priority;
    private String            comments;
    private String            labtech;
    private Long              id;
    private String            patient;
    private String            status;
    // private String hcp;

    /**
     * Empty constructor for filling in fields without a Prescription object.
     */
    public LabProcedureForm () {
    }

    /**
     * Constructs a new form from the given LabProcedure
     *
     * @param lp
     *            the LabProcedure object
     */
    public LabProcedureForm ( final LabProcedure lp ) {
        this.setCode( lp.getCode() );
        this.setPriority( lp.getPriority() );
        this.setComments( lp.getComments() );
        this.setLabtech( lp.getLabtech().getUsername() );
        this.setPatient( lp.getPatient().getUsername() );
        this.setStatus( lp.getStatus() );
    }

    /**
     * Constructs a LabProcedureForm from the given fields
     *
     * @param code
     *            the LOINC code of the procedure
     * @param priority
     *            the priority of the procedure
     * @param comment
     *            the comment for the procedure
     * @param labtech
     *            the labtech assigned to the procedure
     * @param id
     *            the id of the procedure
     * @param status
     *            the status of the procedure
     */
    public LabProcedureForm ( final LabProcedureCode code, final int priority, final String comment,
            final String labtech, final Long id, final String status ) {
        this.code = code;
        this.priority = priority;
        this.comments = comment;
        this.labtech = labtech;
        this.id = id;
        this.status = status;
    }

    /**
     * Sets the LOINC code
     *
     * @param code
     *            the LOINC code
     */
    public void setCode ( final LabProcedureCode code ) {
        this.code = code;
    }

    /**
     * Sets the priority of the lab procedure
     *
     * @param priority
     *            the priority of the lab procedure
     */
    public void setPriority ( final int priority ) {
        this.priority = priority;
    }

    /**
     * Sets the comment of the lab procedure
     *
     * @param comment
     *            the comment to set
     */
    public void setComments ( final String comment ) {
        this.comments = comment;
    }

    /**
     * Sets the lab tech of the lab procedure
     *
     * @param labtech
     *            the labtech of the lab procedure
     */
    public void setLabtech ( final String labtech ) {
        this.labtech = labtech;
    }

    /**
     * Sets the id of the lab procedure
     *
     * @param id
     *            the id of the lab procedure
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns the code of the lab procedure
     *
     * @return the code of the lab procedure
     */
    public LabProcedureCode getCode () {
        return code;
    }

    /**
     * Returns the priority of the lab procedure
     *
     * @return the priority of the lab procedure
     */
    public int getPriority () {
        return priority;
    }

    /**
     * Returns the comment of the lab procedure
     *
     * @return the comment of the lab procedure
     */
    public String getComment () {
        return comments;
    }

    /**
     * Returns the labtech of the lab procedure
     *
     * @return the labtech of the lab procedure
     */
    public String getLabtech () {
        return labtech;
    }

    /**
     * Returns the id of the lab procedure
     *
     * @return the id of the lab procedure
     */
    public Long getId () {
        return id;
    }

    /**
     * Returns the patient associated with the lab procedure
     *
     * @return the patient associated with the lab procedure
     */
    public String getPatient () {
        return patient;
    }

    /**
     * Set the patient
     *
     * @param patient
     *            the patient to set
     */
    public void setPatient ( final String patient ) {
        this.patient = patient;
    }

    /**
     * @return the status
     */
    public String getStatus () {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus ( final String status ) {
        this.status = status;
    }

    /**
     * Returns the hcp associated with the lab procedure
     *
     * @return the hcp associated with the lab procedure
     */
    /**
     * public String getHcp() { return hcp; }
     */

    /**
     * Sets the hcp associated with the lab procedure
     *
     * @param hcp
     *            the hcp associated with the lab procedure
     */
    /**
     * public void setHcp(String hcp) { this.hcp = hcp; }
     */

}
