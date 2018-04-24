package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.PasswordResetToken;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;
import edu.ncsu.csc.itrust2.models.persistent.Vaccine;
import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Tests for Immunizations and Vaccines
 * @author Abeer Altaf and Hannah Morrison
 *
 */
public class ImmunizationsStepDefs {

	private WebDriver                driver;
    private final String             baseUrl = "http://localhost:8080/iTrust2";
    private static final String BASE_URL  = "http://localhost:8080/iTrust2/";
    private static final String VISIT_URL = BASE_URL + "hcp/documentOfficeVisit.html";
    private static final String VIEW_URL  = BASE_URL + "patient/viewImmunizations.html";

    // Token for testing
    private final PasswordResetToken token   = null;
    WebDriverWait                    wait;

    @Before
    public void setup () {
    	ChromeDriverManager.getInstance().setup();
        final ChromeOptions options = new ChromeOptions();
        //options.addArguments( "headless" );
        options.addArguments( "window-size=1200x600" );
        options.addArguments( "blink-settings=imagesEnabled=false" );
        driver = new ChromeDriver( options );
        wait = new WebDriverWait( driver, 15 );
    }
    
    @After
    public void tearDown () {
        driver.close();
        driver.quit();
    }
    
    private void setTextField ( final By byval, final Object value ) {
        final WebElement elem = driver.findElement( byval );
        elem.clear();
        elem.sendKeys( value.toString() );
    }
    
    private void enterValue ( final String name, final String value ) {
        final WebElement field = driver.findElement( By.name( name ) );
        field.clear();
        field.sendKeys( String.valueOf( value ) );
    }

    private void selectItem ( final String name, final String value ) {
        final By selector = By.xpath( "//input[@value='" + value + "']" );
        wait.until( ExpectedConditions.visibilityOfElementLocated( selector ) );
        final WebElement element = driver.findElement( selector );
        element.click();
    }
    
    private void selectName ( final String name ) {
        final WebElement element = driver.findElement( By.cssSelector( "input[name='" + name + "']" ) );
        element.click();
    }
    
    private String getUserName ( final String first, final String last ) {
        return first.substring( 0, 1 ).toLowerCase() + last.toLowerCase();
    }

    @Given ( "I login as admin" )
    public void login ( ) {
    	driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "admin" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }
    
    @Given ( "I login with username: (.+)" )
    public void login ( final String username ) {
        driver.get( baseUrl );

        enterValue( "username", username );
        enterValue( "password", "123456" );
        driver.findElement( By.className( "btn" ) ).click();
    }
    
    @Given ( "required patient exists" )
    public void loadRequiredUsers () throws ParseException {

        // make sure the patient we need to login exist

        final Patient dbJim = Patient.getByName( "jbean" );
        final Patient jbean = null == dbJim ? new Patient() : dbJim;
        jbean.setSelf( User.getByName( "jbean" ) );
        jbean.setFirstName( "Jim" );
        jbean.setLastName( "Bean" );
        jbean.setEmail( "jbean@gmail.com" );
        jbean.setAddress1( "123 Jim Bean St." );
        jbean.setCity( "Raleigh" );
        jbean.setState( State.NC );
        jbean.setZip( "12345" );
        jbean.setPhone( "123-456-7890" );

        final SimpleDateFormat sdf = new SimpleDateFormat( "MM/DD/YYYY", Locale.ENGLISH );
        final Calendar time = Calendar.getInstance();
        time.setTime( sdf.parse( "09/22/1985" ) );
        jbean.setDateOfBirth( time );

        jbean.setBloodType( BloodType.BNeg );

        jbean.setEthnicity( Ethnicity.Caucasian );
        jbean.setGender( Gender.Male );

        jbean.save();
    }
    
    @When ( "I started documenting an office visit for the patient with name: (.+) (.+) and date of birth: (.+)" )
    public void startOfficeVisit ( final String firstName, final String lastName, final String dob ) {
        driver.get( VISIT_URL );
        final String patient = getUserName( firstName, lastName );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "[value='" + patient + "']" ) ) );
        selectItem( "name", patient );
    }

    @When ( "filled in the office visit with date: (.+), hospital: (.+), notes: (.*), weight: (.+), height: (.+), blood pressure: (.+), household smoking status: (.+), patient smoking status: (.+), hdl: (.+), ldl: (.+), and triglycerides: (.+)" )
    public void fillOfficeVisitForm ( final String date, final String hospital, final String notes, final String weight,
            final String height, final String bloodPressure, final String hss, final String pss, final String hdl,
            final String ldl, final String triglycerides ) {
        enterValue( "date", date );
        enterValue( "time", "10:10 AM" );
        selectItem( "hospital", hospital );
        enterValue( "notes", notes );
        enterValue( "weight", weight );
        enterValue( "height", height );
        enterValue( "systolic", bloodPressure.split( "/" )[0] );
        enterValue( "diastolic", bloodPressure.split( "/" )[1] );
        selectItem( "houseSmokingStatus", hss );
        selectItem( "patientSmokingStatus", pss );
        enterValue( "hdl", hdl );
        enterValue( "ldl", ldl );
        enterValue( "tri", triglycerides );
    }

    @When ( "add an immunization for (.+)" )
    public void addImmunization ( final String vaccine) throws InterruptedException {
    	Thread.sleep(5000);
    	wait.until( ExpectedConditions.elementToBeClickable( By.xpath( "//input[@name='" + vaccine + "']" ) ) );
        driver.findElement( By.name( "fillImmunization" ) ).click();
        //assertEquals( "", driver.findElement( By.name( "errorMsg" ) ).getText() );
    }

    @When ( "submitted the office visit" )
    public void submitOfficeVisit () {
        driver.findElement( By.name( "submit" ) ).click();
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.name( "success" ), " " ) );
    }

    @Then ( "A message indicated the visit was submitted successfully" )
    public void officeVisitSuccessful () {
        final WebElement msg = driver.findElement( By.name( "success" ) );
        assertEquals( "Office visit created successfully", msg.getText() );
    }

    @When ( "I choose to view my immunizations" )
    public void viewImmunizations () {
        driver.get( VIEW_URL );
    }

    @Then ( "I see an immunization for (.+)" )
    public void immunizationVisible ( final String vaccine) {
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.tagName( "body" ), vaccine ) );
        final List<WebElement> rows = driver.findElements( By.name( "immunizationTableRow" ) );

//        List<WebElement> data = null;
//        for ( final WebElement r : rows ) {
//            if ( r.getText().contains( vaccine ) ) {
//                data = r.findElements( By.tagName( "td" ) );
//                break;
//            }
//        }
//
//        assertEquals( vaccine, data.get( 0 ).getText() );
    }

    @When ( "I choose to add a new vaccine" )
    public void addVaccine () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('vaccines').click();" );
    }

    @When ( "submit the values for CPT (.+), name (.+), and description (.+)" )
    public void submitVaccine ( final String cpt, final String name, final String description )
            throws InterruptedException {
    	final WebElement vaccine = driver.findElement( By.name( "vaccine" ) );
    	vaccine.clear();
    	vaccine.sendKeys( name );
        final WebElement code = driver.findElement( By.name( "code" ) );
        code.clear();
        code.sendKeys( cpt );
        final WebElement desc = driver.findElement( By.name( "description" ) );
        desc.clear();
        desc.sendKeys( description );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();
    }

    @Then ( "the vaccine (.+) is successfully added to the system" )
    public void vaccineSuccessful ( final String vaccine ) throws InterruptedException {
    	assertTrue( driver.getPageSource().contains( "Existing" ) );
    }

}