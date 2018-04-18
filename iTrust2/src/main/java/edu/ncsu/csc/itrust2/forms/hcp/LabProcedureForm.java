package edu.ncsu.csc.itrust2.forms.hcp;

import java.io.Serializable;

import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;

public class LabProcedureForm implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String            code;
    private int               priority;
    private String            comments;
    private String            labtech;
    private Long              id;

    /**
     * Empty constructor for filling in fields without a Prescription object.
     */
    public LabProcedureForm () {
    }

    public LabProcedureForm ( final LabProcedure lp ) {
        this.setCode( lp.getCode().getCode() );
        this.setPriority( lp.getPriority() );
        this.setComment( lp.getNotes() );
        this.setLabtech( lp.getLabtech().getUsername() );
    }

    public LabProcedureForm ( final String code, final int priority, final String comment, final String labtech,
            final Long id ) {
        this.code = code;
        this.priority = priority;
        this.comments = comment;
        this.labtech = labtech;
        this.id = id;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode ( final String code ) {
        this.code = code;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority ( final int priority ) {
        this.priority = priority;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment ( final String comment ) {
        this.comments = comment;
    }

    /**
     * @param labtech
     *            the labtech to set
     */
    public void setLabtech ( final String labtech ) {
        this.labtech = labtech;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    /**
     * @return the code
     */
    public String getCode () {
        return code;
    }

    /**
     * @return the priority
     */
    public int getPriority () {
        return priority;
    }

    /**
     * @return the comment
     */
    public String getComment () {
        return comments;
    }

    /**
     * @return the labtech
     */
    public String getLabtech () {
        return labtech;
    }

    /**
     * @return the id
     */
    public Long getId () {
        return id;
    }

}
