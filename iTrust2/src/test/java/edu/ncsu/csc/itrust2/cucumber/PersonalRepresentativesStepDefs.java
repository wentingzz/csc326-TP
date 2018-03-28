package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        final WebElement patientRep = driver
                .findElement( By.xpath( "/tr[@name='representativeTableRow']/input[@value=" + name + "]" ) );
        patientRep.click();
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
        final WebElement selectElement = driver.findElement( By.id( "reps" ) );
        final Select patientDropdown = new Select( selectElement );
        patientDropdown.selectByVisibleText( name );
        // assuming value for this button will be addRepSubmit
        final WebElement addRepresentative = driver.findElement( By.xpath( "//button[@name='submit']" ) );
        addRepresentative.click();
    }

    @Then ( "(.+) is successfully added as a representative." )
    public void representativeSuccesfullyAdded ( final String name ) {
        assertTrue( driver.getPageSource().contains( "Representative added successfully" ) );
    }

    @When ( "I remove the representative (.+) by clicking the 'Delete' button for that representative." )
    public void removePersonalRep ( final String name ) {
        // assuming value for this button will be deleteRepSubmit
        final WebElement deleteRepresentative = driver.findElement( By.name( "deleteRepresentative" ) );
        deleteRepresentative.click();

    }

    @Then ( "(.+) is succesfully added as a representative." )
    public void checkRepIsAdded ( final String name ) {
        assertTrue( driver.getPageSource().contains( name ) );
    }

    @Then ( "Then that representative (.+) does not appear." )
    public void checkDeletedRepIsGone ( final String name ) {
        assertFalse( driver.getPageSource().contains( name ) );
    }
}
