package edu.ncsu.csc.itrust2.controllers.patient;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ncsu.csc.itrust2.forms.hcp_patient.PatientForm;
import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Controller for the Patient landing screen and basic patient information
 *
 * @author Kai Presler-Marshall
 *
 */
@Controller
public class PatientController {

    /**
     * Returns the form page for a patient to view all OfficeVisits
     *
     * @param model
     *            The data for the front end
     * @return Page to display to the user
     */
    @GetMapping ( "/patient/viewOfficeVisits" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewOfficeVisits ( final Model model ) {
        return "/patient/viewOfficeVisits";
    }

    /**
     * Returns the form page for a patient to view all prescriptions
     *
     * @param model
     *            The data for the front end
     * @return Page to display to the user
     */
    @GetMapping ( "/patient/viewPrescriptions" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewPrescriptions ( final Model model ) {
        return "/patient/viewPrescriptions";
    }

    /**
     * Returns the form page for a patient to view all lab
     *
     * @param model
     *            The data for the front end
     * @return Page to display to the user
     */
    @GetMapping ( "/patient/viewLabProcedures" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewLabProcedures ( final Model model ) {
        return "/patient/viewLabProcedures";
    }

    /**
     * Returns the form page for a patient to view all immunizations
     *
     * @param model
     *            The data for the front end
     * @return Page to display to the user
     */
    @GetMapping ( "/patient/viewImmunizations" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewImmunizations ( final Model model ) {
        return "/patient/viewImmunizations";
    }

    /**
     * Landing screen for a Patient when they log in
     *
     * @param model
     *            The data from the front end
     * @return The page to show to the user
     */
    @RequestMapping ( value = "patient/index" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String index ( final Model model ) {
        return edu.ncsu.csc.itrust2.models.enums.Role.ROLE_PATIENT.getLanding();
    }

    /**
     * Provides the page for a User to view and edit their demographics
     *
     * @param model
     *            The data for the front end
     * @return The page to show the user so they can edit demographics
     */
    @GetMapping ( value = "patient/editDemographics" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewDemographics ( final Model model ) {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        final PatientForm form = new PatientForm( Patient.getPatient( self ) );
        model.addAttribute( "PatientForm", form );
        LoggerUtil.log( TransactionType.VIEW_DEMOGRAPHICS, self );
        return "/patient/editDemographics";
    }

    /**
     * Processes the Edit Demographics form for a Patient
     *
     * @param form
     *            Form from the user to parse and validate
     * @param result
     *            The validation result on the firm
     * @param model
     *            Data from the front end
     * @return Page to show to the user
     */
    @PostMapping ( "/patient/editDemographics" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String demographicsSubmit ( @Valid @ModelAttribute ( "PatientForm" ) final PatientForm form,
            final BindingResult result, final Model model ) {
        Patient p = null;
        try {
            p = new Patient( form );
            p.setSelf( User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() ) );
        }
        catch ( final Exception e ) {
            e.printStackTrace( System.out );
            if ( e.getMessage().equals( "First name must contain 1-20 characters (alphanumeric, -, ', or space)" ) ) {

                result.rejectValue( "firstName", "firstName.notvalid",
                        "First name must contain 1-20 characters (alphanumeric, -, ', or space)" );
            }

            if ( e.getMessage().equals( "Last name must contain 1-30 characters (alphanumeric, -, ', or space)" ) ) {

                result.rejectValue( "lastName", "lastName.notvalid",
                        "Last name must contain 1-30 characters (alphanumeric, -, ', or space)" );
            }

            if ( e.getMessage().equals( "Phone number must be of the form XXX-XXX-XXXX (digits only)" ) ) {

                result.rejectValue( "phone", "phone.notvalid",
                        "Phone number must be of the form XXX-XXX-XXXX (digits only)" );
            }

            if ( e.getMessage().contains( "Illegal pattern character" ) ) {
                result.rejectValue( "dateOfBirth", "dateOfBirth.notvalid", "Expected format: MM/DD/YYYY" );
            }
        }

        if ( result.hasErrors() ) {
            model.addAttribute( "PatientForm", form );
            return "/patient/editDemographics";
        }
        else {
            // Delete the patient so that the cache has to refresh.
            final Patient oldPatient = Patient.getByName( p.getSelf().getUsername() );
            if ( oldPatient != null ) {
                oldPatient.delete();
            }
            p.save();
            LoggerUtil.log( TransactionType.EDIT_DEMOGRAPHICS,
                    SecurityContextHolder.getContext().getAuthentication().getName() );
            return "patient/editDemographicsResult";
        }
    }

    /**
     * Provides the page for a User to view and edit their representatives
     *
     * @param model
     *            The data for the front end
     * @return The page to show the user so they can edit representatives
     */
    @GetMapping ( value = "/patient/editPersonalRepresentatives" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String editRepresentatives ( final Model model ) {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        final PatientForm form = new PatientForm( Patient.getPatient( self ) );
        model.addAttribute( "PatientForm", form );
        LoggerUtil.log( TransactionType.UNDECLARE_PERSONAL_REPRESENTATIVES, self );
        return "/patient/editPersonalRepresentatives";
    }

    /**
     * Provides the page for a User to view their representatives
     *
     * @param model
     *            The data for the front end
     * @return The page to show the user so they can edit representatives
     */
    @GetMapping ( value = "/patient/viewPersonalRepresentatives" )
    @PreAuthorize ( "hasRole('ROLE_PATIENT')" )
    public String viewRepresentatives ( final Model model ) {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        final PatientForm form = new PatientForm( Patient.getPatient( self ) );
        model.addAttribute( "PatientForm", form );
        LoggerUtil.log( TransactionType.VIEW_PERSONAL_REPRESENTATIVES, self );
        return "/patient/viewPersonalRepresentatives";
    }

}
