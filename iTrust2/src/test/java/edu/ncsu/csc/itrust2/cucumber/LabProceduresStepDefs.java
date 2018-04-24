package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

    @Given ( "I log in with username (.+) and password (.+)" )
    public void login ( final String usernameToUse, final String passwordToUse ) {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( usernameToUse );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( passwordToUse );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    @When ( "I go to the Lab Procedures page" )
    public void goToLabProceduresPage () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('manageLabProcedureCodes').click();" );

    }

    @When ( "I fill in values in the Add Lab Procedure form with (.+) and (.+) and (.+) and (.+)" )
    public void fillLabProcedureForm ( final String procName, final String procCode, final String procComponents,
            final String procProperty ) throws InterruptedException {
        Thread.sleep( 6000 );
        final WebElement procedureName = driver.findElement( By.name( "longCommonName" ) );
        procedureName.clear();
        procedureName.sendKeys( procName );
        final WebElement procedureCode = driver.findElement( By.name( "code" ) );
        procedureCode.clear();
        procedureCode.sendKeys( procCode );
        final WebElement procedureComponents = driver.findElement( By.name( "component" ) );
        procedureComponents.clear();
        procedureComponents.sendKeys( procComponents );
        final WebElement procedureProperty = driver.findElement( By.name( "property" ) );
        procedureProperty.clear();
        procedureProperty.sendKeys( procProperty );
        final WebElement submit = driver.findElement( By.name( "add" ) );
        submit.click();
    }

    @Then ( "The lab procedure (.+) was created successfully." )
    public void verifyProcedureCreated ( final String procCode ) {
        assertTrue( driver.getPageSource().contains( "Existing" ) );
    }

    @When ( "I start to fill out the form to document the office visit, including adding a lab procedure with (.+) and (.+) and (.+) and (.+)" )
    public void beginDocumentingOfficeVisit ( final String procCode, final String procPriority,
            final String procComments, final String procAssignedTech ) {
        // ( (JavascriptExecutor) driver ).executeScript(
        // "document.getElementById('documentOfficeVisit').click();" );
        //
        // wait.until( ExpectedConditions.and(
        // ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ),
        // ExpectedConditions.visibilityOfElementLocated( By.name( "name" ) ),
        // ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" )
        // ) ) );
        //
        // final WebElement notes = driver.findElement( By.name( "notes" ) );
        // notes.clear();
        // notes.sendKeys( "none" );
        //
        // final WebElement patient = driver.findElement( By.name( "name" ) );
        // patient.click();
        // final WebElement type = driver.findElement( By.name( "type" ) );
        // type.click();
        //
        // final WebElement hospital = driver.findElement( By.name( "hospital" )
        // );
        // hospital.click();
        //
        // final WebElement date = driver.findElement( By.name( "date" ) );
        // date.clear();
        // date.sendKeys( "12/19/2027" );
        //
        // final WebElement time = driver.findElement( By.name( "time" ) );
        // time.clear();
        // time.sendKeys( "9:30 AM" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "height" ) ) );
        // final WebElement heightElement = driver.findElement( By.name(
        // "height" ) );
        // heightElement.clear();
        // heightElement.sendKeys( "120" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "weight" ) ) );
        // final WebElement weightElement = driver.findElement( By.name(
        // "weight" ) );
        // weightElement.clear();
        // weightElement.sendKeys( "120" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "systolic" ) ) );
        // final WebElement systolicElement = driver.findElement( By.name(
        // "systolic" ) );
        // systolicElement.clear();
        // systolicElement.sendKeys( "100" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "diastolic" ) ) );
        // final WebElement diastolicElement = driver.findElement( By.name(
        // "diastolic" ) );
        // diastolicElement.clear();
        // diastolicElement.sendKeys( "100" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "hdl" ) ) );
        // final WebElement hdlElement = driver.findElement( By.name( "hdl" ) );
        // hdlElement.clear();
        // hdlElement.sendKeys( "90" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "ldl" ) ) );
        // final WebElement ldlElement = driver.findElement( By.name( "ldl" ) );
        // ldlElement.clear();
        // ldlElement.sendKeys( "100" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "tri" ) ) );
        // final WebElement triElement = driver.findElement( By.name( "tri" ) );
        // triElement.clear();
        // triElement.sendKeys( "100" );
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated(
        // By.cssSelector( "input[value=\"" +
        // HouseholdSmokingStatus.NONSMOKING.toString() + "\"]" ) ) );
        // final WebElement houseSmokeElement = driver.findElement(
        // By.cssSelector( "input[value=\"" +
        // HouseholdSmokingStatus.NONSMOKING.toString() + "\"]" ) );
        // houseSmokeElement.click();
        //
        // wait.until( ExpectedConditions.visibilityOfElementLocated(
        // By.cssSelector( "input[value=\"" +
        // PatientSmokingStatus.NEVER.toString() + "\"]" ) ) );
        // final WebElement patientSmokeElement = driver
        // .findElement( By.cssSelector( "input[value=\"" +
        // PatientSmokingStatus.NEVER.toString() + "\"]" ) );
        // patientSmokeElement.click();
        // final WebElement labProcedureCode = driver.findElement( By.id(
        // procCode ) );
        // labProcedureCode.clear();
        // labProcedureCode.click();
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.name(
        // "submit" ) ) );
        // //final WebElement procedurePriority = driver.findElement( By.id(
        // "priority" + procPriority ) );
        // final WebElement procedurePriority = driver.findElement( By.xpath(
        // "//input[@ng-model='" + procPriority + "']" ) );
        // procedurePriority.click();
        // final WebElement procedureComments = driver.findElement( By.name(
        // "comments" ) );
        // procedureComments.clear();
        // procedureComments.sendKeys( procComments );
        // final WebElement procedureAssignedTech = driver.findElement(
        // By.xpath( "//input[@value='" + procAssignedTech + "']" ) );
        // procedureAssignedTech.click();
        // final WebElement submit = driver.findElement( By.name( "submit" ) );
        // submit.click();
    }

    @When ( "I go to the View Lab Procedures page" )
    public void goToLabProceduresPageLabTech () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('viewLabProcedures').click();" );

    }

    @When ( "I am able to access a page with the heading \"Procedures\"" )
    public void canSeeLabProceduresPage () {
        assertTrue( driver.getPageSource().contains( "Procedures" ) );
    }

    @Then ( "The ofice visit is documented successfully" )
    public void documentedSuccessfully () {
        // wait.until( ExpectedConditions.textToBePresentInElementLocated(
        // By.name( "success" ),
        // "Office visit created successfully" ) );
        // assertTrue( driver.getPageSource().contains( "Office Visit" ) );
    }

    // Referenced this StackOverflow post:
    // https://sqa.stackexchange.com/questions/28330/how-to-get-nested-web-element-in-dynamic-table-using-xpath
    @When ( "I edit the components field of (.+) to be (.+)" )
    public void editLabProcedureComponent ( final String code, final String newComponents ) {
        driver.manage().timeouts().implicitlyWait( 15, TimeUnit.SECONDS );
        final WebElement editButton = driver.findElement(
                By.xpath( "//tr[.//td[contains(text(),'" + code + "')]]//input[@name='editLabProcedureCodes']" ) );
        editButton.click();
        final WebElement componentsField = driver.findElement( By.xpath( "//input[@name='component']" ) );
        componentsField.clear();
        componentsField.sendKeys( newComponents );
        final WebElement updateButton = driver.findElement( By.xpath( "//button[@name='edit']" ) );
        updateButton.click();
    }

    @Then ( "the lab procedure, (.+), has a components field that is edited successfully, and contains (.+)." )
    public void verifyProcedureEdited ( final String code, final String newComponents ) throws InterruptedException {
        // check that the new component is on the page by getting the text of
        // the entire page
        // wait for the page to load
        Thread.sleep( 10000 );
        final String page = driver.findElement( By.tagName( "body" ) ).getText();
        assertTrue( page.contains( newComponents ) );
    }

    @When ( "I click the delete button for (.+)" )
    public void deleteLabProcedure ( final String code ) throws InterruptedException {
        Thread.sleep( 10000 );
        final WebElement deleteButton = driver.findElement(
                By.xpath( "//tr[.//td[contains(text(),'" + code + "')]]//input[@name='deleteLabProcedureCodes']" ) );
        deleteButton.click();
    }

    @Then ( "the lab procedure, (.+), is deleted successfully." )
    public void verifyProcedureIsDeleted ( final String code ) throws InterruptedException {
        // check that the code is not on the page by getting the text of
        // the entire page
        // wait for the page to load
        Thread.sleep( 10000 );
        final String page = driver.findElement( By.tagName( "body" ) ).getText();
        assertFalse( page.contains( code ) );
    }

}
