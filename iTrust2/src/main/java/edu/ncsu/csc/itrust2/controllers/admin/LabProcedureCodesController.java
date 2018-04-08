package edu.ncsu.csc.itrust2.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller enables Admins to add and delete Lab Procedure Codes to the
 * system
 *
 * @author Azra Shaikh
 *
 */

@Controller
public class LabProcedureCodesController {

    /**
     * Add code
     *
     * @param model
     *            data for front end
     * @return mapping
     */
    @RequestMapping ( value = "admin/manageLabProcedureCodes" )
    @PreAuthorize ( "hasRole('ROLE_ADMIN')" )
    public String addCode ( final Model model ) {
        return "/admin/manageLabProcedureCodes";
    }

}
