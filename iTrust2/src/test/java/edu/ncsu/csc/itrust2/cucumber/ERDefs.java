package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.enums.BloodType;
import edu.ncsu.csc.itrust2.models.enums.Ethnicity;
import edu.ncsu.csc.itrust2.models.enums.Gender;
import edu.ncsu.csc.itrust2.models.enums.State;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class ERDefs {

    private WebDriver    driver;
    private final String baseUrl = "http://localhost:8080/iTrust2";

    WebDriverWait        wait;

    @Before
    public void setup () {

        driver = new HtmlUnitDriver( true );
        wait = new WebDriverWait( driver, 20 );
    }

    @After
    public void tearDown () {
        driver.quit();
    }

    private void setTextField ( final By byval, final String value ) {
        final WebElement elem = driver.findElement( byval );
        elem.clear();
        elem.sendKeys( value );
    }

    @Given ( "all the required users exist" )
    public void loadRequiredUsers () throws ParseException {

        final SimpleDateFormat sdf = new SimpleDateFormat( "MM/DD/YYYY", Locale.ENGLISH );
        final Calendar time = Calendar.getInstance();

        // set Nellie Sanderson's demographics

        final Patient dbNellie = Patient.getByName( "nsanderson" );

        final Patient nsanderson = null == dbNellie ? new Patient() : dbNellie;
        nsanderson.setSelf( User.getByName( "nsanderson" ) );
        nsanderson.setFirstName( "Nellie" );
        nsanderson.setLastName( "Sanderson" );
        nsanderson.setEmail( "nsanderson@gmail.com" );
        nsanderson.setAddress1( "987 Nellie Sanderson Dr." );
        nsanderson.setCity( "Greensboro" );
        nsanderson.setState( State.NC );
        nsanderson.setZip( "27410" );
        nsanderson.setPhone( "946-832-4961" );
        time.setTime( sdf.parse( "04/24/1993" ) );
        nsanderson.setDateOfBirth( time );
        nsanderson.setBloodType( BloodType.ABPos );
        nsanderson.setEthnicity( Ethnicity.Caucasian );
        nsanderson.setGender( Gender.Female );

        nsanderson.save();

    }

    @Given ( "Dr Shelly Vang has logged in and chosen to view a patient's emergency record" )
    public void gotoEmergencyRecordsPage () throws Exception {
        driver.get( baseUrl );
        setTextField( By.name( "username" ), "svang" );
        setTextField( By.name( "password" ), "123456" );
        driver.findElement( By.className( "btn" ) ).click();

        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('viewEmegencyRecords').click();" );
    }

    @When ( "she selects a patient with first name: (.+) and last name: (.+)" )
    public void selectPatient ( final String first, final String last ) throws Exception {
        final String username = first.toLowerCase().charAt( 0 ) + last.toLowerCase();

        // wait for the patients to load before searching
        wait.until( ExpectedConditions
                .visibilityOfElementLocated( By.cssSelector( "input[type=radio][value=" + username + "]" ) ) );
        driver.findElement( By.cssSelector( "input[type=radio][value=" + username + "]" ) ).click();
    }

    @Then ( "The correct (.+), (.+), (.+), (.+), and (.+) are displayed" )
    public void checkList ( final String fullname, final String age, final String dob, final String gender,
            final String bloodtype ) {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "fullname" ) ) );
        assertEquals( fullname, driver.findElement( By.id( "fullname" ) ).getText() );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "age" ) ) );
        assertEquals( age, driver.findElement( By.id( "age" ) ).getText() );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "dob" ) ) );
        assertEquals( dob, driver.findElement( By.id( "dob" ) ).getText() );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "gender" ) ) );
        assertEquals( gender, driver.findElement( By.id( "gender" ) ).getText() );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "bloodtype" ) ) );
        assertEquals( bloodtype, driver.findElement( By.id( "bloodtype" ) ).getText() );
    }

}
