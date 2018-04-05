package edu.ncsu.csc.itrust2.models.persistent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * represents the lab procedure codes that a lab procedure will have; these will
 * show up on Office Visit forms to represent a Lab Procedure
 *
 * @author Hannah Morrison (hmorris3)
 *
 */
public class LabProcedureCode {
    private String code;
    private String description;

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
     * sets the description for the lab procedure
     *
     * @param description
     *            the description of the lab procedure
     */
    public void setDescription ( final String description ) {
        this.description = description;
    }

    /**
     * returns the code for the lab procedure code
     *
     * @return code
     */
    public String getCode () {
        return code;
    }

    /**
     * returns the description for the lab procedure code
     *
     * @return description
     */
    public String getDescription () {
        return description;
    }
}
