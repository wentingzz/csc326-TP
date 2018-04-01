package edu.ncsu.csc.itrust2.controllers.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.itrust2.models.enums.TransactionType;
import edu.ncsu.csc.itrust2.models.persistent.Diagnosis;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.utils.LoggerUtil;

/**
 * Class that provided the REST endpoints for dealing with diagnoses. Diagnoses
 * can either be retrieved individually by ID, or in lists by office visit or by
 * patient.
 *
 * @author Thomas
 *
 */
@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APIDiagnosisController extends APIController {

    /**
     * Returns the Diagnosis with the specified ID.
     *
     * @param id
     *            The id of the diagnosis to retrieved
     * @return Response Entity containing the diagnosis if it exists
     */
    @GetMapping ( BASE_PATH + "/diagnosis/{id}" )
    public ResponseEntity getDiagnosis ( @PathVariable ( "id" ) final Long id ) {
        final Diagnosis d = Diagnosis.getById( id );
        LoggerUtil.log( TransactionType.DIAGNOSIS_VIEW_BY_ID, LoggerUtil.currentUser(),
                "Retrieved diagnosis with id " + id );
        return null == d
                ? new ResponseEntity( errorResponse( "No Diagnosis found for id " + id ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( d, HttpStatus.OK );
    }

    /**
     * Returns a list of diagnoses for a specified office visit
     *
     * @param id
     *            The ID of the office visit to get diagnoses for
     * @return List of Diagnosis objects for the given visit
     */
    @GetMapping ( BASE_PATH + "/diagnosesforvisit/{id}" )
    public List<Diagnosis> getDiagnosesForVisit ( @PathVariable ( "id" ) final Long id ) {
        // Check if office visit exists
        if ( OfficeVisit.getById( id ) == null ) {
            return null;
        }
        LoggerUtil.log( TransactionType.DIAGNOSIS_VIEW_BY_OFFICE_VISIT, LoggerUtil.currentUser(),
                OfficeVisit.getById( id ).getPatient().getUsername(),
                "Retrieved diagnoses for office visit with id " + id );
        return Diagnosis.getByVisit( id );
    }

    /**
     * Returns a list of diagnoses for the logged in patient
     *
     * @return List of Diagnoses for the patient
     */
    @GetMapping ( BASE_PATH + "/diagnoses" )
    public List<Diagnosis> getDiagnosis () {
        final User self = User.getByName( SecurityContextHolder.getContext().getAuthentication().getName() );
        if ( self == null ) {
            return null;
        }
        LoggerUtil.log( TransactionType.DIAGNOSIS_PATIENT_VIEW_ALL, self.getUsername(),
                self.getUsername() + " viewed their diagnoses" );

        return Diagnosis.getForPatient( self );
    }
    
    /**
     * Returns a list of diagnoses for the logged in patient
     *
     * @return List of Diagnoses for the patient
     */
    @GetMapping ( BASE_PATH + "/patientDiagnoses/{username}" )
    public List<Diagnosis> getPatientDiagnoses ( @PathVariable ( "username" ) final String username ) {
    		final User patient = User.getByName(username);
    		List<Diagnosis> diagnosis = Diagnosis.getForPatient(patient);
    		List<Diagnosis> between = new ArrayList<Diagnosis>();
    		Calendar endDate = Calendar.getInstance();
    		Calendar startDate = Calendar.getInstance();
    		startDate.add(Calendar.DAY_OF_MONTH, -60);
    		for (int i = 0; i < diagnosis.size(); i++) {
    			final Calendar current = diagnosis.get(i).getVisit().getDate();
    			if(current.compareTo(startDate) >= 0 && current.compareTo(endDate) <= 0) {
    				between.add(diagnosis.get(i));
    			}
    		}
    		
    		between.sort(new Comparator<Object>() {
    			@Override
    			public int compare( final Object arg0, final Object arg1) {
    				return ((Diagnosis) arg1).getVisit().getDate().compareTo(((Diagnosis) arg0).getVisit().getDate());
    			}
    		});
    		if ( patient == null ) {
            return null;
        }
        LoggerUtil.log( TransactionType.VIEW_ER_REPORT, LoggerUtil.currentUser() + " viewed the emergency report" );
        

       // Collections.reverse(between);
        return between;
    }

}