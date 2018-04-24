package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertTrue;

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

/**
 * Tests the bug fix for issue 53, where the user, upon deleting without
 * checking the check box, would be redirected to a blank page without
 * indication that the hospital had been deleted
 *
 * @author Hannah
 *
 */
public class DeleteHospitalStepDefs {
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

    @Given ( "I am able to log in as an admin" )
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

    @When ( "I navigate to the Delete Hospital page" )
    public void goToDeleteUserPage () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('deletehospital').click();" );
    }

    @When ( "I click the radio button for (.+)" )
    public void selectUserToDelete ( final String name ) {
        final WebElement userButton = driver.findElement( By.xpath( "//input[@id='" + name + "']" ) );
        userButton.click();
    }

    @When ( "I press submit without checking the check box" )
    public void pressSubmit () {
        final WebElement submitButton = driver.findElement( By.xpath( "//button[@type='submit']" ) );
        submitButton.click();
    }

    /**
     * The issue was that not checking the box would redirect the user to a
     * blank page; Now, an error message is shown and the user is not
     * redirected, so look for something on the delete page to prove the user
     * has not been redirected to a different page
     */
    @Then ( "I am not directed to a blank page" )
    public void checkNotRedirected () {
        assertTrue( driver.getPageSource().contains( "Delete" ) );
    }
}
