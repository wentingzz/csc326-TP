package edu.ncsu.csc.itrust2.apitest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import edu.ncsu.csc.itrust2.forms.admin.LabProcedureCodeForm;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedureCode;
import edu.ncsu.csc.itrust2.mvc.config.WebMvcConfiguration;

/**
 * tests the API functionality for Lab Procedure Codes
 *
 * @author Hannah
 *
 */
@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { RootConfiguration.class, WebMvcConfiguration.class } )
@WebAppConfiguration
public class APILabProcedureCodeTest {
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
     * tests getting the list of codes
     *
     * @throws Exception
     */
    @Test
    public void testGetting () throws Exception {
        mvc.perform( get( "/api/v1/labprocedurecodes" ) ).andExpect( status().isOk() );
    }

    /**
     * tests adding a code to the list of codes
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "admin", roles = { "ADMIN" } )
    public void testInvalidOperations () throws Exception {
        // make the test date a random constant date/time;
        // to use the current date/time, can use empty Date() constructor
        final Date testDate = new Date( 9998999899L );
        // create a form with valid information filled in
        final LabProcedureCodeForm form = new LabProcedureCodeForm();
        form.setId( (long) 1 );
        form.setCode( "88573-1" );
        form.setLongCommonName( "Onchocherca sp IgG2 Ab [Presence] in Serum by Immunoassay" );
        form.setComponent( "Onchochera sp Ab.IgG2" );
        form.setProperty( "PrThr" );
        form.setDateCreated( testDate );
        // make a LabProcedureCode object using the form created above
        final LabProcedureCode code = new LabProcedureCode( form );
        // try to get a code that doesn't exist;
        // it should be not found
        mvc.perform( get( "/api/v1/labprocedurecode" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( (long) 1 ) ) ).andExpect( status().isNotFound() );
        // delete a code that doesn't exist
        mvc.perform( delete( "/api/v1/labprocedurecode/1" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );
        // add a code to the list of codes
        // mvc.perform( post( "/api/v1/labprocedurecodes" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( form ) ) ).andExpect(
        // status().isOk() );
    }

}
