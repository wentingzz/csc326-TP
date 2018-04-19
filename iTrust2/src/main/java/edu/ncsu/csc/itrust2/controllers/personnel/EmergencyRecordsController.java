package edu.ncsu.csc.itrust2.controllers.personnel;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring controller for the webpages associated with veiwing emergency records
 *
 * @author Azra Shaikh
 *
 */
@Controller
public class EmergencyRecordsController {

    /**
     * Creates the form page for the View Emergency Records
     *
     * @param model
     *            Data for the front end
     * @return Page to show to the user
     */
    @RequestMapping ( value = "personnel/emergencyRecords" )
    @PreAuthorize ( "hasRole('ROLE_ER') or hasRole('ROLE_HCP')" )
    public String viewEmegencyRecords ( final Model model ) {
        return "/personnel/emergencyRecords";
    }
}