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
 * tests the bug fix for issue 52 by checking that the admin is not shown a
 * blank page when trying to delete a user without checking the submit box
 *
 * @author Hannah
 *
 */
public class DeleteUserStepDefs {
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

    @Given ( "I, an admin, am able to log in" )
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

    @When ( "I navigate to the Delete User page" )
    public void goToDeleteUserPage () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('deleteuser').click();" );
    }

    @When ( "I select the radio button for (.+)" )
    public void selectUserToDelete ( final String username ) {
        final WebElement userButton = driver.findElement( By.xpath( "//input[@id='" + username + "']" ) );
        userButton.click();
    }

    @When ( "I press submit without checking the checkbox" )
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
    @Then ( "I am not redirected to a blank page" )
    public void checkNotRedirected () {
        assertTrue( driver.getPageSource().contains( "Delete" ) );
    }
}
