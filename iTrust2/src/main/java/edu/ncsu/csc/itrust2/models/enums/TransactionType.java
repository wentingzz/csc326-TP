package edu.ncsu.csc.itrust2.models.enums;

/**
 * A TransactionType represents an event that took place in the system and that
 * is to be logged. This is used to provide a code that can easily be saved in
 * the database and a longer description of it that can be displayed to the
 * user. Also stores whether the event is patient-visible.
 *
 * As new functionality is added to iTrust2, add in new TransactionType codes
 * representing the event.
 *
 * @author Kai Presler-Marshall
 *
 */
public enum TransactionType {

    /**
     * Failed login
     */
    LOGIN_FAILURE ( 1, "Failed login", true ),
    /**
     * Successful login
     */
    LOGIN_SUCCESS ( 2, "Successful login", true ),
    /**
     * User logged out
     */
    LOGOUT ( 3, "Logged Out", true ),
    /**
     * User locked out of system (temporary)
     */
    USER_LOCKOUT ( 4, "User Locked Out", true ),
    /**
     * IP locked out of system (temporary)
     */
    IP_LOCKOUT ( 5, "IP Locked Out", true ),
    /**
     * User banned
     */
    USER_BANNED ( 6, "User Banned", true ),
    /**
     * IP Banned
     */
    IP_BANNED ( 7, "IP Banned", true ),
    /**
     * New User created
     */
    CREATE_USER ( 100, "New user created", false ),
    /**
     * User was viewed
     */
    VIEW_USER ( 101, "Single user viewed", false ),
    /**
     * Multiple users viewed
     */
    VIEW_USERS ( 102, "List of users viewed", false ),
    /**
     * User deleted
     */
    DELETE_USER ( 103, "User deleted", false ),
    /**
     * User changed/updated
     */
    UPDATE_USER ( 104, "User updated", false ),

    /**
     * User viewed their demographics
     */
    VIEW_DEMOGRAPHICS ( 400, "Demographics viewed by user", true ),
    /**
     * User updated their demographics
     */
    EDIT_DEMOGRAPHICS ( 410, "Demographics edited by user", true ),

    /**
     * User creates their demographics
     */
    CREATE_DEMOGRAPHICS ( 411, "Demographics created by user", true ),

    /**
     * Hospital created
     */
    CREATE_HOSPITAL ( 500, "New hospital created", false ),
    /**
     * Hospital viewed by user
     */
    VIEW_HOSPITAL ( 501, "Hospital viewed", false ),
    /**
     * Hospital modified by user
     */
    EDIT_HOSPITAL ( 502, "Hospital edited", false ),
    /**
     * Hospital deleted
     */
    DELETE_HOSPITAL ( 503, "Hospital deleted", false ),

    /**
     * Upcoming appointment viewed by Patient or HCP
     */
    VIEW_SCHEDULED_APPOINTMENT ( 611, "Upcoming appointment viewed", true ),
    /**
     * AppointmentRequest submitted by patient
     */
    APPOINTMENT_REQUEST_SUBMITTED ( 640, "Appointment requested by patient", true ),
    /**
     * AppointmentRequest viewed
     */
    APPOINTMENT_REQUEST_VIEWED ( 641, "Appointment request(s) viewed", true ),
    /**
     * AppointmentRequest canceled/deleted by patient
     */
    APPOINTMENT_REQUEST_DELETED ( 642, "Appointment request deleted by patient", true ),
    /**
     * AppointmentRequest approved by HCP
     */
    APPOINTMENT_REQUEST_APPROVED ( 650, "Appointment request approved by HCP", true ),
    /**
     * AppointmentRequest denied by HCP
     */
    APPOINTMENT_REQUEST_DENIED ( 651, "Appointment request denied by HCP", true ),
    /**
     * AppointmentRequest otherwise updated
     */
    APPOINTMENT_REQUEST_UPDATED ( 652, "Appointment request was updated", true ),

    /**
     * Create basic health metrics
     */
    OFFICE_VISIT_CREATE ( 800, "Create office visit for patient", true ),
    /**
     * HCP views basic health metrics
     */
    OFFICE_VISIT_HCP_VIEW ( 801, "View office visit by HCP", true ),
    /**
     * HCP edits basic health metrics
     */
    OFFICE_VISIT_EDIT ( 802, "HCP edits basic health metrics", true ),
    /**
     * Patient views basic health metrics for an office visit
     */
    OFFICE_VISIT_PATIENT_VIEW ( 810, "View office visit by Patient", true ),

    /**
     * Office visit is deleted
     */
    OFFICE_VISIT_DELETE ( 811, "Office visit deleted", true ),

    /**
     * Admin adds an ICD-10 code
     */
    ICD_CREATE ( 1001, "Admin adds ICD-10 code", false ),
    /**
     * Admin deletes an ICD10 code
     */
    ICD_DELETE ( 1002, "Admin deletes ICD-10 code", false ),
    /**
     * Admin edits ICD-10 code
     */
    ICD_EDIT ( 1003, "Admin edits ICD-10 code", false ),
    /**
     * Admin views ICD-10 code
     */
    ICD_VIEW ( 1004, "Administrator views ICD-10 codes", false ),
    /**
     * Admin views all ICD-10 code
     */
    ICD_VIEW_ALL ( 1005, "Administrator views all ICD-10 codes", false ),
    /**
     * User gets diagnosis by id
     */
    DIAGNOSIS_VIEW_BY_ID ( 1006, "Diagnoses retrieved by id", true ),
    /**
     * User gets diagnoses for an office visit
     */
    DIAGNOSIS_VIEW_BY_OFFICE_VISIT ( 1007, "Diagnoses retrieved by office visit", true ),
    /**
     * Patient views diagnoses
     */
    DIAGNOSIS_PATIENT_VIEW_ALL ( 1008, "Patient views diagnoses", true ),
    /**
     * HCP creates diagnosis
     */
    DIAGNOSIS_CREATE ( 1009, "HCP creates a diagnosis within and office visit", true ),
    /**
     * HCP edits diagnosis
     */
    DIAGNOSIS_EDIT ( 1010, "HCP edits diagnosis", true ),
    /**
     * HCP deletes diagnosis
     */
    DIAGNOSIS_DELETE ( 1011, "HCP deletes diagnosis", true ),

    /**
     * Admin created a new drug
     */
    DRUG_CREATE ( 900, "Admin created a new drug", true ),
    /**
     * Admin edited an existing drug
     */
    DRUG_EDIT ( 901, "Admin edited an existing drug", true ),
    /**
     * Admin deleted an existing drug
     */
    DRUG_DELETE ( 902, "Admin deleted an existing drug", true ),
    /**
     * Admin views all drugs in the system
     */
    DRUG_VIEW ( 903, "Admin views all drugs in the system", true ),

    /**
     * HCP created a new prescription
     */
    PRESCRIPTION_CREATE ( 910, "HCP created a new prescription", true ),
    /**
     * HCP edited an existing prescription
     */
    PRESCRIPTION_EDIT ( 911, "HCP edited an existing prescription", true ),
    /**
     * HCP deleted an existing prescription
     */
    PRESCRIPTION_DELETE ( 912, "HCP deleted an existing prescription", true ),
    /**
     * User viewed an existing prescription
     */
    PRESCRIPTION_VIEW ( 913, "User viewed an existing prescription", true ),

    /**
     * Attempt to update password fails
     */
    PASSWORD_UPDATE_FAILURE ( 1100, "Failed password update", true ),

    /**
     * Attempt to update password is successful
     */
    PASSWORD_UPDATE_SUCCESS ( 1101, "Successful password update", true ),
    /**
     * Reset request email sent successfully
     */
    PASSWORD_RESET_EMAIL_SENT ( 1102, "Reset request email sent", true ),

    /**
     * HCP views patient's demographics
     */
    PATIENT_DEMOGRAPHICS_VIEW ( 1200, "HCP views patient's demographics", true ),
    /**
     * HCP edits patient's demographics
     */
    PATIENT_DEMOGRAPHICS_EDIT ( 1201, "HCP edits patient's demographics", true ),

    /**
     * User views their log entries
     */
    VIEW_USER_LOG ( 1301, "Log events viewed", true ),
    /**
     * An email is sent to the user on password change
     */
    CREATE_PW_CHANGE_EMAIL ( 1401, "PW Change Email notification sent", true ),
    /**
     * An email is sent to the user on appointment request change
     */
    CREATE_APPOINTMENT_REQUEST_EMAIL ( 1402, "AppointmentRequest Email notification sent", true ),
    /**
     * An email is sent to the user on lockout
     */
    CREATE_LOCKOUT_EMAIL ( 1403, "Account Lockout Email notification sent", true ),
    /**
     * An email would be sent but email address is missing.
     */
    CREATE_MISSING_EMAIL_LOG ( 1404, "Email notification could not be sent due to missing email address", true );

    /**
     * Creates a TransactionType for logging events
     *
     * @param code
     *            Code of the event
     * @param description
     *            Description of the event that occurred
     * @param patientViewable
     *            Whether this logged event can be viewed by the patient
     *            involved
     */
    private TransactionType ( final int code, final String description, final boolean patientViewable ) {
        this.code = code;
        this.description = description;
        this.patientView = patientViewable;
    }

    /**
     * Code of the TransactionType, from the iTrust2 wiki.
     */
    private int     code;
    /**
     * Description of the event
     */
    private String  description;
    /**
     * Whether the patient can view the event
     */
    private boolean patientView;

    /**
     * Retrieves the code of this TransactionType
     *
     * @return Code of the event
     */
    public int getCode () {
        return code;
    }

    /**
     * Description of this TransactionType event
     *
     * @return Description of the event
     */
    public String getDescription () {
        return description;
    }

    /**
     * Retrieves if the Patient can view this event
     *
     * @return Patient viewable or not
     */
    public boolean isPatientViewable () {
        return patientView;
    }

}
