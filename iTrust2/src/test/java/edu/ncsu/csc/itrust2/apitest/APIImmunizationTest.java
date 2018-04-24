package edu.ncsu.csc.itrust2.apitest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.itrust2.config.RootConfiguration;
import edu.ncsu.csc.itrust2.forms.hcp.ImmunizationForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * tests the Immunizations API functionality
 *
 * @author Hannah
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APIImmunizationTest {
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * Sets up test
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    /**
     * tests Immunizations functionality for HCPs
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testImmunizationsAsHCP () throws Exception {
        // get all immunizations
        mvc.perform( get( "/api/v1/immunizations" ) ).andExpect( status().isOk() );

        // create an immunization
        final ImmunizationForm form = new ImmunizationForm();
        // create a patient for the form
        final User testPatient = new User( "isThereLifeOnMars",
                "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Role.ROLE_PATIENT, 1 );
        // use setters
        form.setId( (long) 1 );
        form.setPatient( testPatient.getUsername() );
        form.setVaccine( "Chicken Pox" );
        // create the immunization
        // mvc.perform( post( "/api/v1/immunizations" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( form ) ) ).andExpect(
        // status().isOk() );

        // get all immunizations the patient has received
        mvc.perform( get( "/api/v1/patientImmunizations/patient" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );

    }
}
