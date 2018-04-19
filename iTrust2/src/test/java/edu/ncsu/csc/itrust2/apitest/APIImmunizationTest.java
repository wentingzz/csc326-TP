package edu.ncsu.csc.itrust2.apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.ncsu.csc.itrust2.config.RootConfiguration;
import edu.ncsu.csc.itrust2.forms.admin.VaccineForm;
import edu.ncsu.csc.itrust2.forms.admin.UserForm;
import edu.ncsu.csc.itrust2.forms.hcp.ImmunizationForm;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.persistent.Immunization;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * Class for testing immunization API.
 *
 * @author Apeksha Awasthi
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APIImmunizationTest {
    private MockMvc               mvc;

    private Gson                  gson;
    VaccineForm                      vaccineForm;

    @Autowired
    private WebApplicationContext context;

    /**
     * Performs setup operations for the tests.
     *
     * @throws Exception
     */
    @Before
    @WithMockUser ( username = "admin", roles = { "USER", "ADMIN" } )
    public void setup () throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
        gson = new GsonBuilder().create();
        final UserForm patientForm = new UserForm( "api_test_patient", "123456", Role.ROLE_PATIENT, 1 );
        mvc.perform( post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patientForm ) ) );

        // Create vaccine for testing
        vaccineForm = new VaccineForm();
        vaccineForm.setCode( "00002" );
        vaccineForm.setName( "TEST" );
        vaccineForm.setDescription( "DESC" );
        mvc.perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vaccineForm ) ) );
    }

    /**
     * Tests basic immunization APIs.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "hcp", roles = { "USER", "HCP", "ADMIN" } )
    public void testImmunizationAPI () throws Exception {

        // Create two immunization forms for testing
        final ImmunizationForm form1 = new ImmunizationForm();
        form1.setVaccine( vaccineForm.getCode() );
        form1.setPatient( "api_test_patient" );

        final ImmunizationForm form2 = new ImmunizationForm();
        form2.setVaccine( vaccineForm.getCode() );
        form2.setPatient( "api_test_patient" );
       // form2.setId(1504l);

        // Add first immunization to system
        final String content1 = mvc
                .perform( post( "/api/v1/immunizations" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( form1 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        // Parse response as Immunization
        final Immunization im1 = gson.fromJson( content1, Immunization.class );
        final ImmunizationForm im1Form = new ImmunizationForm( im1 );
        assertEquals(form1.getVaccine(), im1Form.getVaccine());
        assertEquals( form1.getPatient(), im1Form.getPatient() );
        form1.setId(1504l);
   //     assertEquals(form1.getId(), im1Form.getId());
        

        // Add second immunization to system
        final String content2 = mvc
                .perform( post( "/api/v1/immunizations" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( form2 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final Immunization im2 = gson.fromJson( content2, Immunization.class );
        final ImmunizationForm im2Form = new ImmunizationForm( im1 );
        assertEquals(form1.getVaccine(), im2Form.getVaccine());
        assertEquals( form1.getPatient(), im2Form.getPatient() );
     //   form2.setId(1573l);
   //     assertEquals(form2.getId(), im2Form.getId());

        // Verify immunizations have been added
        final String allImmunizationContent = mvc.perform( get( "/api/v1/immunizations" ) ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        final List<Immunization> allImmunizations = gson.fromJson( allImmunizationContent,
                new TypeToken<List<Immunization>>() {
                }.getType() );
        assertTrue( allImmunizations.size() >= 2 );

        // Create vaccine for testing
        VaccineForm vaccineForm2 = new VaccineForm();
        vaccineForm2.setCode( "00007" );
        vaccineForm2.setName( "TEST2" );
        vaccineForm2.setDescription( "DESC2" );
        mvc.perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vaccineForm ) ) );
        
        Vaccine vaccine = new Vaccine(vaccineForm2);
        // Edit first immunization
        im1.setVaccine(vaccine);
        final String editContent = mvc
                .perform( put( "/api/v1/immunizations" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( new ImmunizationForm( im1 ) ) ) )
                .andReturn().getResponse().getContentAsString();
        final Immunization edited = gson.fromJson( editContent, Immunization.class );
     //   assertEquals( im1.getId(), edited.getId() );

        ImmunizationForm getImForm = new ImmunizationForm();
        // Get single immunization
        final String getContent = mvc
                .perform( get( "/api/v1/immunizations" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( getImForm ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
       // final Immunization fetched = gson.fromJson( getContent, Immunization.class );
        //assertEquals( im1.getId(), fetched.getId() );
        
        // Get list of all patient immunizations
        @SuppressWarnings("unused")
		String pres = mvc
                .perform( get( "/api/v1/patientImmunizations/" + im1.getPatient().getUsername() ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( new ImmunizationForm( ) ) )).andReturn()
                        .getResponse().getContentAsString(); 
        
        //get a single immunization with an id
        
        pres = mvc
        		.perform( put( "/api/v1/immunizations" + im1.getId() ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new ImmunizationForm( ) ) ) )
        .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();

        // Attempt invalid edit
    //    im1.setId(-5l);
        mvc.perform( put( "/api/v1/immunizations" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new ImmunizationForm( ) ) ) ).andExpect( status().isBadRequest() );
      //  im1.setId(1504l);
        // Delete test objects
        mvc.perform( delete( "/api/v1/immunizations/" + im1.getId() ) ).andExpect( status().isOk() )
                .andExpect( content().string( im1.getId().toString() ) );
     //   im2.setId(1504l);
        mvc.perform( delete( "/api/v1/immunizations/" + im2.getId() ) ).andExpect( status().isOk() )
                .andExpect( content().string( im2.getId().toString() ) );
    }

}