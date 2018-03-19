package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.PatientSmokingStatus;
import edu.ncsu.csc.itrust2.models.persistent.PasswordResetToken;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class LabProceduresStepDefs {

    private WebDriver                driver;
    private final String             baseUrl = "http://localhost:8080/iTrust2";

    // Token for testing
    private final PasswordResetToken token   = null;
    WebDriverWait                    wait;

    @Before
    public void setup () {
        // driver = new HtmlUnitDriver( true );

        ChromeDriverManager.getInstance().setup();
        final ChromeOptions options = new ChromeOptions();
        options.addArguments( "headless" );
        options.addArguments( "window-size=1200x600" );
        options.addArguments( "blink-settings=imagesEnabled=false" );
        driver = new ChromeDriver( options );
        wait = new WebDriverWait( driver, 10 );

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

    @Given ( "I can login as admin" )
    public void adminLogin () {
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

    @When ( "I go to the Lab Procedures page" )
    public void goToLabProceduresPage () {
        // assuming id will be labProcedures
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('labProcedures').click();" );
    }

    @When ( "I fill in values in the Add Lab Procedure form with (.+) and (.+)" )
    public void fillLabProcedureForm ( final String procName, final String procCode ) {
        // assuming ID for this will be procedurename
        final WebElement procedureName = driver.findElement( By.id( "procedurename" ) );
        procedureName.clear();
        procedureName.sendKeys( procName );
        // assuming ID for this will be procedurename
        final WebElement procedureCode = driver.findElement( By.id( "procedurecode" ) );
        procedureCode.clear();
        procedureCode.sendKeys( procCode );
    }

    @Then ( "The lab procedure was created successfully." )
    public void verifyProcedureCreated () {
        assertTrue( driver.getPageSource().contains( "Lab procedure added successfully" ) );
    }

    @When ( "When I start fill out the form to document the office visit, including adding a lab procedure with name (.+) and code (.+)" )
    public void beginDocumentingOfficeVisit ( final String procName, final String procCode ) {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('documentOfficeVisit').click();" );

        wait.until( ExpectedConditions.and( ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ),
                ExpectedConditions.visibilityOfElementLocated( By.name( "name" ) ),
                ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" ) ) ) );

        final WebElement notes = driver.findElement( By.name( "notes" ) );
        notes.clear();
        notes.sendKeys( "nonee" );

        final WebElement patient = driver.findElement( By.name( "name" ) );
        patient.click();
        final WebElement type = driver.findElement( By.name( "type" ) );
        type.click();

        final WebElement hospital = driver.findElement( By.name( "hospital" ) );
        hospital.click();

        final WebElement date = driver.findElement( By.name( "date" ) );
        date.clear();
        date.sendKeys( "12/19/2027" );

        final WebElement time = driver.findElement( By.name( "time" ) );
        time.clear();
        time.sendKeys( "9:30 AM" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "height" ) ) );
        final WebElement heightElement = driver.findElement( By.name( "height" ) );
        heightElement.clear();
        heightElement.sendKeys( "120" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "weight" ) ) );
        final WebElement weightElement = driver.findElement( By.name( "weight" ) );
        weightElement.clear();
        weightElement.sendKeys( "120" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "systolic" ) ) );
        final WebElement systolicElement = driver.findElement( By.name( "systolic" ) );
        systolicElement.clear();
        systolicElement.sendKeys( "100" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "diastolic" ) ) );
        final WebElement diastolicElement = driver.findElement( By.name( "diastolic" ) );
        diastolicElement.clear();
        diastolicElement.sendKeys( "100" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "hdl" ) ) );
        final WebElement hdlElement = driver.findElement( By.name( "hdl" ) );
        hdlElement.clear();
        hdlElement.sendKeys( "90" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "ldl" ) ) );
        final WebElement ldlElement = driver.findElement( By.name( "ldl" ) );
        ldlElement.clear();
        ldlElement.sendKeys( "100" );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "tri" ) ) );
        final WebElement triElement = driver.findElement( By.name( "tri" ) );
        triElement.clear();
        triElement.sendKeys( "100" );

        wait.until( ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector( "input[value=\"" + HouseholdSmokingStatus.NONSMOKING.toString() + "\"]" ) ) );
        final WebElement houseSmokeElement = driver.findElement(
                By.cssSelector( "input[value=\"" + HouseholdSmokingStatus.NONSMOKING.toString() + "\"]" ) );
        houseSmokeElement.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector( "input[value=\"" + PatientSmokingStatus.NEVER.toString() + "\"]" ) ) );
        final WebElement patientSmokeElement = driver
                .findElement( By.cssSelector( "input[value=\"" + PatientSmokingStatus.NEVER.toString() + "\"]" ) );
        patientSmokeElement.click();
        // assuming id will be name
        final WebElement labProcedureName = driver.findElement( By.id( "name" ) );
        labProcedureName.clear();
        labProcedureName.sendKeys( procName );
        // assuming id will be code
        final WebElement labProcedureCode = driver.findElement( By.id( "code" ) );
        labProcedureCode.clear();
        labProcedureCode.sendKeys( procCode );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();
    }

    @When ( "I am able to access a page with the heading \"Procedures\"" )
    public void canSeeLabProceduresPage () {
        assertTrue( driver.getPageSource().contains( "Procedures" ) );
    }

}
