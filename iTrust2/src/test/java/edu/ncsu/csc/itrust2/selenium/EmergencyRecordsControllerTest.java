package edu.ncsu.csc.itrust2.selenium;

import java.util.logging.Level;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class EmergencyRecordsControllerTest {

    static {
        java.util.logging.Logger.getLogger( "com.gargoylesoftware" ).setLevel( Level.OFF );
    }

    private final WebDriver driver  = new HtmlUnitDriver( true );
    private final String    baseUrl = "http://localhost:8080/iTrust2";

    // /**
    // * Setup for test
    // */
    // @Before
    // public void setup () {
    // driver.get( baseUrl );
    //
    // driver.findElement( By.name( "Admin" ) ).click();
    // driver.get( baseUrl + "/admin/addUser" );
    //
    // driver.findElement( By.name( "Username:" ) ).sendKeys( "test" );
    // driver.findElement( By.name( "Password:" ) ).sendKeys( "123456" );
    // driver.findElement( By.name( "Password (again):" ) ).sendKeys( "123456"
    // );
    // final Select dropdown = new Select( driver.findElement( By.name( "Role:"
    // ) ) );
    // dropdown.selectByVisibleText( "ROLE_PATIENT" );
    // driver.findElement( By.name( "Add User" ) ).click();
    //
    // driver.findElement( By.name( "Log out" ) ).click();
    //
    // driver.findElement( By.name( "HCP" ) ).click();
    // driver.get( baseUrl + "/hcp/documentOfficeVisit.html" );
    //
    // driver.findElement( By.name( "Date:" ) ).sendKeys( "3/27/2018" );
    // driver.findElement( By.name( "Time:" ) ).sendKeys( "7:00 PM" );
    // driver.findElement( By.name( "test" ) ).click();
    // driver.findElement( By.name( "GENERAL_CHECKUP" ) ).click();
    // driver.findElement( By.name( "General Hospital" ) ).click();
    //
    // driver.findElement( By.name( "Height/Length:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "Weight:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "Systolic:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "Diastolic:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "HDL:" ) ).sendKeys( "50" );
    // driver.findElement( By.name( "LDL:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "Triglycerides:" ) ).sendKeys( "222" );
    // driver.findElement( By.name( "NONSMOKING" ) ).click();
    // driver.findElement( By.name( "NEVER" ) ).click();
    //
    // driver.findElement( By.name( "Top Quality" ) ).click();
    // driver.findElement( By.name( "Date:" ) ).sendKeys( "3/27/2018" );
    // driver.findElement( By.name( "Add Diagnosis" ) ).click();
    //
    // driver.findElement( By.name( "Li208" ) ).click();
    // driver.findElement( By.name( "Dosage:" ) ).sendKeys( "5" );
    // driver.findElement( By.name( "Start Date:" ) ).sendKeys( "1/1/2018" );
    // driver.findElement( By.name( "End Date:" ) ).sendKeys( "03/31/2018" );
    // driver.findElement( By.name( "Renewals:" ) ).sendKeys( "2" );
    //
    // driver.findElement( By.name( "Submit Office Visit" ) ).click();
    // driver.findElement( By.name( "Log out" ) ).click();
    // }

    /**
     * Test Emergency Records Page
     */
    @Test
    public void testViewEmergenctRecords () {
        driver.get( baseUrl );
        final WebElement username = driver.findElement( By.name( "username" ) );
        username.clear();
        username.sendKeys( "er" );
        final WebElement password = driver.findElement( By.name( "password" ) );
        password.clear();
        password.sendKeys( "123456" );
        final WebElement submit = driver.findElement( By.className( "btn" ) );
        submit.click();

        driver.get( baseUrl + "personnel/emergencyRecords" );

        // final WebElement patient = driver.findElement( By.name( "test" ) );
        // patient.click();
    }

}
