package edu.ncsu.csc.itrust2.apitest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.hamcrest.Matchers;
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

import edu.ncsu.csc.itrust2.config.RootConfiguration;
import edu.ncsu.csc.itrust2.forms.admin.VaccineForm;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * Class for testing vaccine API.
 * 
 * @author Apeksha Awasthi <aawasth>
 * @author Abeer Altaf <aaltaf>
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APIVaccineTest {
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
     * Tests basic vaccine API functionality.
     *
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "admin", roles = { "USER", "ADMIN" } )
    public void testVaccineAPI () throws UnsupportedEncodingException, Exception {
        // Create vaccine for testing
        final VaccineForm form1 = new VaccineForm();
        form1.setCode( "00000" );
        form1.setName( "TEST1" );
        form1.setDescription( "DESC1" );

        final VaccineForm form2 = new VaccineForm();
        form2.setCode( "00001" );
        form2.setName( "TEST2" );
        form2.setDescription( "Desc2" );

        
        // Add vaccine1 to system
        final String content1 = mvc
                .perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( form1 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        // Parse response as Vaccine object
        final Gson gson = new GsonBuilder().create();
        final Vaccine vaccine1 = gson.fromJson( content1, Vaccine.class );
        assertEquals( form1.getCode(), vaccine1.getCode() );
        assertEquals( form1.getName(), vaccine1.getName() );
        assertEquals( form1.getDescription(), vaccine1.getDescription() );

        // Attempt to add same vaccine twice
        mvc.perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( form1 ) ) ).andExpect( status().isConflict() );

        
        // Add vaccine2 to system
        final String content2 = mvc
                .perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( form2 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final Vaccine vaccine2 = gson.fromJson( content2, Vaccine.class );
        assertEquals( form2.getCode(), vaccine2.getCode() );
        assertEquals( form2.getName(), vaccine2.getName() );
        assertEquals( form2.getDescription(), vaccine2.getDescription() );

        // Verify vaccines have been added
        mvc.perform( get( "/api/v1/vaccines" ) ).andExpect( status().isOk() )
                .andExpect( content().string( Matchers.containsString( form1.getCode() ) ) )
                .andExpect( content().string( Matchers.containsString( form2.getCode() ) ) );

        // Edit first vaccine's description
        vaccine1.setDescription( "This is a better description" );
        final String editContent = mvc
                .perform( put( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( vaccine1 ) ) )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();
        final Vaccine editedVaccine = gson.fromJson( editContent, Vaccine.class );
        assertEquals( vaccine1.getId(), editedVaccine.getId() );
        assertEquals( vaccine1.getCode(), editedVaccine.getCode() );
        assertEquals( vaccine1.getName(), editedVaccine.getName() );
        assertEquals( "This is a better description", editedVaccine.getDescription() );

        // Attempt invalid edit with duplicate code
        vaccine2.setCode( "00000" );
        mvc.perform( put( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vaccine2 ) ) ).andExpect( status().isConflict() );

        // Attempt invalid edit with invalid code
        vaccine2.setCode( "0000003" );
        mvc.perform( put( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vaccine2 ) ) ).andExpect( status().isBadRequest() );
        
        // Follow up with valid edit
        vaccine2.setCode( "00003" );
        mvc.perform( put( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vaccine2 ) ) ).andExpect( status().isOk() );

        // Create vaccine for testing
        final VaccineForm form3 = new VaccineForm();
        form1.setCode( "000005" );
        form1.setName( "TEST3" );
        form1.setDescription( "DESC3" );
        
        //Add an invalid code for a vaccine.
        mvc.perform( post( "/api/v1/vaccines" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( form3 ) ) ).andExpect( status().isBadRequest() );
        
        // Delete test vaccines
        mvc.perform( delete( "/api/v1/vaccines/" + vaccine1.getId() ) ).andExpect( status().isOk() )
                .andExpect( content().string( vaccine1.getId().toString() ) );
        mvc.perform( delete( "/api/v1/vaccines/" + vaccine2.getId() ) ).andExpect( status().isOk() )
                .andExpect( content().string( vaccine2.getId().toString() ) );
        
        //Delete a nonexistent vaccine
        mvc.perform( delete( "/api/v1/vaccines/" + "56789" ) ).andExpect( status().isNotFound() )
        .andExpect( content().string( "{\"status\":\"failed\",\"message\":\"No vaccine found with id 56789\"}"));
    }

}
