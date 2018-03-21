package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import edu.ncsu.csc.itrust2.utils.HibernateDataGenerator;

public class ERDefs {

    private static boolean  initialized = false;

    private final WebDriver driver      = new HtmlUnitDriver( true );
    private final String    baseUrl     = "http://localhost:8080/iTrust2";
    WebDriverWait           wait        = new WebDriverWait( driver, 2 );

    @Before
    public void setup () {
        HibernateDataGenerator.generateUsers();

    }

    private void setTextField ( final By byval, final Object value ) {
        final WebElement elem = driver.findElement( byval );
        elem.clear();
        elem.sendKeys( value.toString() );
    }

    @After
    public void tearDown () {
        driver.close();
    }

    @Given ( "The required ERRecord facilities exist" )
    public void requirementsExist () {
        if ( initialized ) {
            // no need to initialize again
            return;
        }
        // TODO initialize the fields

        initialized = true;
    }

    @When ( "I log in to iTrust2 as an HCP" )
    public void hcpLogin () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "hcp" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    @When ( "I log in to iTrust2 as an ER" )
    public void erLogin () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "testeruser" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "greenball" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();
    }

    @When ( "I go to the Emergency Health Records page" )
    public void navigateToEmergencyHealthRecord () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('emergencyhealthrecord').click();" );
    }

    @When ( "I fill in user type with (.+)" )
    public void fillInfo ( final String userType ) {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "notes" ) ) );
        setTextField( By.name( "usertype" ), userType );
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        driver.findElement( By.name( "submit" ) ).click();
    }

    @Then ( "user's information is shown successfully" )
    public void searchSuccess () {
        try {
            wait.until( ExpectedConditions.textToBePresentInElementLocated( By.name( "success" ),
                    "Office visit created successfully" ) );
        }
        catch ( final Exception e ) {
            fail( driver.findElement( By.name( "success" ) ).getText() );
        }
    }

    @Then ( "The (.+), (.+), (.+), (.+), and (.+) are correct" )
    public void checkList ( final String name, final String age, final String birthday, final String gender,
            final String blood ) {
        // TODO check the information is correct
        // final long time = System.currentTimeMillis();
        // while ( System.currentTimeMillis() - time < 5000 ) {
        // for ( final WebElement diag : driver.findElements( By.name(
        // "diagnosis" ) ) ) {
        // final String text = diag.getText();
        // if ( text.contains( date ) && text.contains( hcp ) && text.contains(
        // description )
        // && text.contains( note ) ) {
        // // we found the right diganosis
        // return;
        // }
        // }
        // }
        // fail( "failed to find specified diagnosis" );
    }

    @Then ( "I see the list of diagnoses codes for the patient" )
    public void seeList () {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "diagnosis" ) ) );
    }

    @Then ( "an error message is shown" )
    public void searchFail () {
        assertTrue( driver.getPageSource().contains( "There are no patients with that MID" ) );
    }
}
