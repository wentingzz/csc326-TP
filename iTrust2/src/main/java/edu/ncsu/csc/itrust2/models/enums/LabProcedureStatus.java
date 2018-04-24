package edu.ncsu.csc.itrust2.models.enums;

/**
 * Status enum that should be used for various different statuses rather than
 * just using Strings or Integers.
 *
 * @author Apeksha
 * @author Abeer
 *
 */
public enum LabProcedureStatus {

    /* Positive statuses */

    /**
     * Lab Procedure has been assigned to labtech by hcp
     */
    ASSIGNED ( 1 ),
    /**
     * lab procedure is in progress
     */
    IN_PROGRESS ( 2 ),

    /**
     * lab procedure is completed
     */
    COMPLETED ( 3 ),

    ;

    /**
     * Code of the status
     */
    private int code;

    /**
     * Create a Status from the numerical code.
     *
     * @param code
     *            Code of the Status
     */
    private LabProcedureStatus ( final int code ) {
        this.code = code;
    }

    /**
     * Gets the numerical Code of the Status
     *
     * @return Code of the Status
     */
    public int getCode () {
        return code;
    }

}
