package edu.ncsu.csc.itrust2.apitest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * tests the API functionality for Lab Procedures
 *
 * @author Hannah
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APILabProcedureTest {
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
     * tests getting lab procedures as an HCP
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testGettingProceduresAsHCP () throws Exception {
        mvc.perform( get( "/api/v1/labProcedures" ) ).andExpect( status().isOk() );
    }

    /**
     * tests getting lab procedures as a patient
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "patient", roles = { "PATIENT" } )
    public void testGettingProceduresAsPatient () throws Exception {
        mvc.perform( get( "/api/v1/labProcedures" ) ).andExpect( status().isOk() );
    }

    /**
     * tests getting lab procedures as a lab tech
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "labtech", roles = { "LABTECH" } )
    public void testGettingProceduresAsLabTech () throws Exception {
        mvc.perform( get( "/api/v1/labtech/labprocedures/" ) ).andExpect( status().isOk() );
    }

    /**
     * tests getting a list of lab techs
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "labtech", roles = { "LABTECH" } )
    public void testGettingLabTechs () throws Exception {
        mvc.perform( get( "/api/v1/labtechs/" ) ).andExpect( status().isOk() );
    }

    /**
     * tests adding a lab procedure
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testInvalid () throws Exception {
        // make the test date a random constant date/time;
        // to use the current date/time, can use empty Date() constructor
        // final Date testDate = new Date( 9998999899L );
        // // create a form with valid information filled in
        // final LabProcedureCodeForm form = new LabProcedureCodeForm();
        // form.setId( (long) 1 );
        // form.setCode( "88573-1" );
        // form.setLongCommonName( "Onchocherca sp IgG2 Ab [Presence] in Serum
        // by Immunoassay" );
        // form.setComponent( "Onchochera sp Ab.IgG2" );
        // form.setProperty( "PrThr" );
        // form.setDateCreated( testDate );
        // // make a LabProcedureCode object using the form created above
        // final LabProcedureCode code = new LabProcedureCode( form );
        // // try to get a code that doesn't exist;
        // // it should be not found
        // final LabProcedureForm procedureForm = new LabProcedureForm();
        // // create the users needed to make a LabProcedure, plus the office
        // visit
        // final User labTech = new User( "labTech1",
        // "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
        // Role.ROLE_LABTECH, 1 );
        // final User testPatient = new User( "testPatient1",
        // "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
        // Role.ROLE_PATIENT, 1 );
        // final User testHCP = new User( "testHCP1",
        // "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.",
        // Role.ROLE_HCP, 1 );
        // final OfficeVisit testVisit = new OfficeVisit();
        // procedureForm.setCode( code );
        // procedureForm.setComments( "none" );
        // procedureForm.setPatient( testPatient.getUsername() );
        // procedureForm.setLabtech( labTech.getUsername() );
        // procedureForm.setPriority( 1 );
        // create a new procedure using the procedureForm
        // mvc.perform( post( "/api/v1/labProcedures" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( procedureForm ) ) ).andExpect(
        // status().isOk() );

        // attempt to delete a procedure that doesn't exist
        mvc.perform( delete( "/api/v1/labprocedures/1/" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );

        // attempt to get a procedure that doesn't exist
        mvc.perform( get( "/api/v1/labProcedures/1" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );
    }

}
