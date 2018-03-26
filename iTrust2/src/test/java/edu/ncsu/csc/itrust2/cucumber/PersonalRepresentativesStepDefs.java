package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

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
import edu.ncsu.csc.itrust2.models.persistent.PasswordResetToken;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class PersonalRepresentativesStepDefs {

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

    @Given ( "I am able to log in to iTrust as (.+) with password (.+)" )
    public void login ( final String username, final String password ) {
        driver.get( baseUrl );
        try {
            driver.findElement( By.id( "logout" ) ).click();
        }
        catch ( final NoSuchElementException nsee ) {
            // user is not logged in, continue
        }

        wait.until( ExpectedConditions.titleIs( "iTrust2 :: Login" ) );
        setTextField( By.name( "username" ), username );
        setTextField( By.name( "password" ), password );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        wait.until( ExpectedConditions.not( ExpectedConditions.titleIs( "iTrust2 :: Login" ) ) );
    }

    // This is the editPersonalRepresentatives page
    @When ( "When I go to the Personal Representatives page" )
    public void goToPersonalRepPage () {
        // assuming will be called personalRepresentatives
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('personalRepresentatives').click();" );
    }

    @When ( "I select patient with the name (.+)" )
    public void selectPatientOnPersonalRepresentativesPage ( final String name ) {
        final WebElement patient = driver.findElement( By.xpath( "//input[@value=name]" ) );
        patient.click();
    }

    @When ( "I add the patient (+) by selecting their name and clicking the 'Add Representative' button" )
    public void addPersonalRep ( final String name ) {
        // assuming ID for this will be repName
        // final WebElement personalRepName = driver.findElement( By.id(
        // "repName" ) );
        // personalRepName.clear();
        // personalRepName.sendKeys( name );
        final WebElement patient = driver.findElement( By.id( name ) );
        patient.click();
        // assuming value for this button will be addRepSubmit
        final WebElement addRepresentative = driver.findElement( By.xpath( "//input[@value='addRepSubmit']" ) );
        addRepresentative.click();
    }

    @Then ( "(.+) is successfully added as a representative." )
    public void representativeSuccesfullyAdded ( final String name ) {
        assertTrue( driver.getPageSource().contains( "Representative added successfully" ) );
    }

    @When ( "I remove the representative <patientrep> by selecting <patientrep> and clicking the 'Remove Patient Representative' button" )
    public void removePersonalRep ( final String name ) {
        final WebElement patientRep = driver
                .findElement( By.xpath( "/tr[@name='representativeTableRow']/input[@value=" + name + "]" ) );
        patientRep.click();
        // assuming value for this button will be deleteRepSubmit
        final WebElement deleteRepresentative = driver.findElement( By.xpath( "//input[@value='deleteRepSubmit']" ) );
        deleteRepresentative.click();

    }
}
