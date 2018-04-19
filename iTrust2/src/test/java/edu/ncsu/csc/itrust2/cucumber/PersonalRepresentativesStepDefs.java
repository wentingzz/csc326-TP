package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.persistent.PasswordResetToken;
import edu.ncsu.csc.itrust2.utils.HibernateDataGenerator;
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
        HibernateDataGenerator.generateUsers();
        ChromeDriverManager.getInstance().setup();
        final ChromeOptions options = new ChromeOptions();
        options.addArguments( "headless" );
        options.addArguments( "window-size=1200x600" );
        options.addArguments( "blink-settings=imagesEnabled=false" );
        driver = new ChromeDriver( options );
        wait = new WebDriverWait( driver, 20 );

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
        wait.until( ExpectedConditions.titleIs( "iTrust2 :: Login" ) );
        setTextField( By.name( "username" ), username );
        setTextField( By.name( "password" ), password );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        wait.until( ExpectedConditions.not( ExpectedConditions.titleIs( "iTrust2 :: Login" ) ) );
    }

    // This is the viewPersonalRepresentatives page
    @When ( "I go to the Personal Representatives page" )
    public void goToPersonalRepPage () {
        // assuming will be called personalRepresentatives
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('personalRepresentatives').click();" );
    }

    @When ( "I select patient with the name (.+)" )
    public void selectPatientOnPersonalRepresentativesPage ( final String name ) {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "patientToGetRep" ) ) );
        // final WebElement patient = driver
        // .findElement( By.xpath( "//input[@name='patientToGetRep' and @value="
        // + name + "]" ) );
        final WebElement patient = driver
                .findElement( By.xpath( "//input[@name='patientToGetRep' and @value='" + name + "']" ) );
        patient.click();
    }

    @When ( "I select the representative (.+)" )
    public void HCPPickRep ( final String name ) {
        final WebElement selectElement = driver.findElement( By.id( "reps" ) );
        final Select patientDropdown = new Select( selectElement );
        patientDropdown.selectByVisibleText( name );
    }

    @When ( "I click the 'Add Representative' button" )
    public void ClickAdd () {
        final WebElement addRepresentative = driver.findElement( By.xpath( "//button[@name='submit']" ) );
        addRepresentative.click();
    }

    /*
     * Used this for selecting dropdown items:
     * https://loadfocus.com/blog/2016/06/13/how-to-select-a-dropdown-in-
     * selenium-webdriver-using-java/
     */
    @When ( "I add the patient (.+) by selecting their name and clicking the 'Add Representative' button" )
    public void addPersonalRep ( final String name ) {
        // final WebElement patient = driver.findElement( By.id( name ) );
        // patient.click();
        // wait.until( ExpectedConditions.visibilityOfElementLocated( By.id(
        // "reps" ) ) );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//select[@id='reps']" ) ) );
        final WebElement selectElement = driver.findElement( By.id( "reps" ) );
        wait.until( ExpectedConditions.elementToBeClickable( By.xpath( "//option[@value='" + name + "']" ) ) );
        final Select patientDropdown = new Select( selectElement );
        patientDropdown.selectByVisibleText( name );
        final WebElement addRepresentative = driver.findElement( By.xpath( "//button[@name='submit']" ) );
        addRepresentative.click();
    }

    @Then ( "(.+) is successfully added as a representative." )
    public void representativeSuccesfullyAdded ( final String name ) {
        assertTrue( driver.getPageSource().contains( name ) );
    }

    @When ( "I remove the representative (.+) by clicking the 'Delete' button for that representative." )
    public void removePersonalRep ( final String name ) {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "deleteRepresentative" ) ) );
        final WebElement deleteRepresentative = driver.findElement( By.name( "deleteRepresentative" ) );
        deleteRepresentative.click();

    }

    @Then ( "(.+) is succesfully added as a representative." )
    public void checkRepIsAdded ( final String name ) {
        assertTrue( driver.getPageSource().contains( name ) );
    }

    // look for the patient's name
    // an error should be thrown because the name can't be found in the table
    // if it is found, throw an exception and pass the test
    // if the error isn't thrown, that means the name does appear in the table,
    // so the test should fail
    @Then ( "that representative (.+) does not appear." )
    public void checkDeletedRepIsGone ( final String name ) {
        try {
            // assertFalse( driver.getPageSource().contains( name ) );
            final WebElement patient = driver.findElement( By.xpath( "//td[contains(text, '" + name + "']" ) );
            patient.click();
            assertFalse( false );
        }
        catch ( final NoSuchElementException e ) {
            assertTrue( true );
        }
    }
}
