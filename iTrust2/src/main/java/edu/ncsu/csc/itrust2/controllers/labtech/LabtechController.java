package edu.ncsu.csc.itrust2.controllers.labtech;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class responsible for managing the behavior for the ER Landing
 * Screen
 *
 * @author Abeer Altaf
 *
 */
@Controller
public class LabtechController {

    /**
     * Returns the Landing screen for the ER
     *
     * @param model
     *            Data from the front end
     * @return The page to display
     */
    @RequestMapping ( value = "labtech/index" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
    public String index ( final Model model ) {
        return edu.ncsu.csc.itrust2.models.enums.Role.ROLE_LABTECH.getLanding();
    }

    /**
     * Returns the Landing screen for the ER
     *
     * Data from the front end
     * 
     * @return The page to display
     */
    @RequestMapping ( value = "labtech/viewlabprocedure" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
    public String viewLabProcedures () {
        // LoggerUtil.log( TransactionType.VIEW_LAB_PROCEDURES, self
        // );
        return "labtech/viewlabprocedure";
    }

}
