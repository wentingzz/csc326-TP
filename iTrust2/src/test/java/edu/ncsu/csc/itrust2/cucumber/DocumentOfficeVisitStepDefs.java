package edu.ncsu.csc.itrust2.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.PatientSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.State;
import edu.ncsu.csc.itrust2.models.persistent.BasicHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.DomainObject;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.Patient;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class DocumentOfficeVisitStepDefs {

    static {
        java.util.logging.Logger.getLogger( "com.gargoylesoftware" ).setLevel( Level.OFF );
    }

    private final WebDriver driver       = new HtmlUnitDriver( true );
    private final String    baseUrl      = "http://localhost:8080/iTrust2";

    private final String    hospitalName = "Office Visit Hospital" + ( new Random() ).nextInt();
    BasicHealthMetrics      expectedBhm;

    WebDriverWait           wait         = new WebDriverWait( driver, 2 );

    @Given ( "The required facilities exist" )
    public void personnelExists () throws Exception {
        OfficeVisit.deleteAll();
        DomainObject.deleteAll( BasicHealthMetrics.class );

        // All tests can safely assume the existence of the 'hcp', 'admin', and
        // 'patient' users

        /* Make sure we create a Hospital and Patient record */

        final Hospital hospital = new Hospital( hospitalName, "Bialystok", "10101", State.NJ.toString() );
        hospital.save();

        /* Create patient record */

        final Patient patient = new Patient();
        patient.setSelf( User.getByName( "patient" ) );
        patient.setFirstName( "Karl" );
        patient.setLastName( "Liebknecht" );
        patient.setEmail( "karl_liebknecht@mail.de" );
        patient.setAddress1( "Karl Liebknecht Haus. Alexanderplatz" );
        patient.setCity( "Berlin" );
        patient.setState( State.DE );
        patient.setZip( "91505" );
        patient.setPhone( "123-456-7890" );
        final SimpleDateFormat sdf = new SimpleDateFormat( "MM/DD/YYYY", Locale.ENGLISH );

        final Calendar time = Calendar.getInstance();
        time.setTime( sdf.parse( "08/13/1871" ) );

        patient.setDateOfBirth( time );

        patient.save();

    }

    @When ( "I log in to iTrust2 as a HCP" )
    public void loginAsHCP () {
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

    @When ( "I navigate to the Document Office Visit page" )
    public void navigateDocumentOV () {
        ( (JavascriptExecutor) driver ).executeScript( "document.getElementById('documentOfficeVisit').click();" );
    }

    @When ( "^I fill in information on the office visit$" )
    public void documentOV () {
        wait.until( ExpectedConditions.and( ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ),
                ExpectedConditions.visibilityOfElementLocated( By.name( "name" ) ),
                ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" ) ) ) );

        final WebElement notes = driver.findElement( By.name( "notes" ) );
        notes.clear();
        notes.sendKeys( "Patient appears pretty much alive" );

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

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();

    }

    @Then ( "The office visit is documented successfully" )
    public void documentedSuccessfully () {
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.name( "success" ),
                "Office visit created successfully" ) );
    }

    /**
     * Ensures that the correct health metrics have been entered
     *
     * @throws InterruptedException
     */
    @Then ( "The basic health metrics for the infant are correct" )
    public void healthMetricsCorrectInfant () throws InterruptedException {
        BasicHealthMetrics actualBhm = null;
        for ( int i = 1; i <= 10; i++ ) {
            try {
                actualBhm = BasicHealthMetrics.getBasicHealthMetrics().get( 0 );
                break;
            }
            catch ( final Exception e ) {
                if ( i == 10 && actualBhm == null ) {
                    fail( "Could not get basic health metrics out of database" );
                }
                Thread.sleep( 1000 );
            }
        }
        assertEquals( expectedBhm.getWeight(), actualBhm.getWeight() );
        assertEquals( expectedBhm.getHeight(), actualBhm.getHeight() );
        assertEquals( expectedBhm.getHeadCircumference(), actualBhm.getHeadCircumference() );
        assertEquals( expectedBhm.getHouseSmokingStatus(), actualBhm.getHouseSmokingStatus() );
    }

    /**
     * Ensures that the correct health metrics have been entered
     *
     * @throws InterruptedException
     */
    @Then ( "The basic health metrics for the child are correct" )
    public void healthMetricsCorrectChild () throws InterruptedException {
        BasicHealthMetrics actualBhm = null;
        for ( int i = 1; i <= 10; i++ ) {
            try {
                actualBhm = BasicHealthMetrics.getBasicHealthMetrics().get( 0 );
                break;
            }
            catch ( final Exception e ) {
                if ( i == 10 && actualBhm == null ) {
                    fail( "Could not get basic health metrics out of database" );
                }
                Thread.sleep( 1000 );
            }
        }
        assertEquals( expectedBhm.getWeight(), actualBhm.getWeight() );
        assertEquals( expectedBhm.getHeight(), actualBhm.getHeight() );
        assertEquals( expectedBhm.getSystolic(), actualBhm.getSystolic() );
        assertEquals( expectedBhm.getDiastolic(), actualBhm.getDiastolic() );
        assertEquals( expectedBhm.getHouseSmokingStatus(), actualBhm.getHouseSmokingStatus() );
    }

    /**
     * Ensures that the correct health metrics have been entered
     *
     * @throws InterruptedException
     */
    @Then ( "The basic health metrics for the adult are correct" )
    public void healthMetricsCorrectAdult () throws InterruptedException {
        BasicHealthMetrics actualBhm = null;
        for ( int i = 1; i <= 10; i++ ) {
            try {
                actualBhm = BasicHealthMetrics.getBasicHealthMetrics().get( 0 );
                break;
            }
            catch ( final Exception e ) {
                if ( i == 10 && actualBhm == null ) {
                    fail( "Could not get basic health metrics out of database" );
                }
                Thread.sleep( 1000 );
            }
        }
        assertEquals( expectedBhm.getWeight(), actualBhm.getWeight() );
        assertEquals( expectedBhm.getHeight(), actualBhm.getHeight() );
        assertEquals( expectedBhm.getSystolic(), actualBhm.getSystolic() );
        assertEquals( expectedBhm.getDiastolic(), actualBhm.getDiastolic() );
        assertEquals( expectedBhm.getHouseSmokingStatus(), actualBhm.getHouseSmokingStatus() );
        assertEquals( expectedBhm.getPatientSmokingStatus(), actualBhm.getPatientSmokingStatus() );
        assertEquals( expectedBhm.getHdl(), actualBhm.getHdl() );
        assertEquals( expectedBhm.getLdl(), actualBhm.getLdl() );
        assertEquals( expectedBhm.getTri(), actualBhm.getTri() );
    }

    /**
     * Ensures that the office visit was not recorded.
     */
    @Then ( "The office visit is not documented" )
    public void notDocumented () {
        wait.until( ExpectedConditions.textToBePresentInElementLocated( By.name( "success" ),
                "Error occurred creating office visit" ) );

        final List<BasicHealthMetrics> list = BasicHealthMetrics.getBasicHealthMetrics();
        assertTrue( 0 == list.size() );
    }

    /**
     * Ensures that a patient exists with the given name and birthday
     *
     * @param name
     *            The name of the patient.
     * @param birthday
     *            The birthday of the patient.
     * @throws ParseException
     */
    @Given ( "^A patient exists with name: (.+) and date of birth: (.+)$" )
    public void patientExistsWithName ( final String name, final String birthday ) throws ParseException {

        final Patient patient = new Patient();
        patient.setSelf( User.getByName( "patient" ) );

        patient.setFirstName( name.split( " " )[0] );
        patient.setLastName( name.split( " " )[1] );
        patient.setEmail( "email@mail.com" );
        patient.setAddress1( "address place. address" );
        patient.setCity( "citytown" );
        patient.setState( State.CA );
        patient.setZip( "91505" );
        patient.setPhone( "123-456-7890" );

        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy", Locale.ENGLISH );
        cal.setTime( sdf.parse( birthday ) );
        patient.setDateOfBirth( cal );

        patient.save();

    }

    /**
     * Documents an office visit with specific information.
     *
     * @param dateString
     *            The current date.
     * @param weightString
     *            The weight of the patient.
     * @param lengthString
     *            The length of the patient.
     * @param headString
     *            The head circumference of the patient.
     * @param smokingStatus
     *            The smoking status of the patient's household.
     * @param note
     *            The note that the doctor includes.
     * @throws InterruptedException
     */
    @When ( "^I fill in information on the office visit for an infant with date: (.+), weight: (.+), length: (.+), head circumference: (.+), household smoking status: (.+), and note: (.+)$" )
    public void documentOVWithSpecificInformation ( final String dateString, final String weightString,
            final String lengthString, final String headString, final String smokingStatus, final String note )
            throws InterruptedException {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "notes" ) ) );
        final WebElement notes = driver.findElement( By.name( "notes" ) );
        notes.clear();
        notes.sendKeys( note );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "input[value=\"patient\"]" ) ) );
        final WebElement patient = driver.findElement( By.cssSelector( "input[value=\"patient\"]" ) );
        patient.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ) );
        final WebElement type = driver.findElement( By.name( "type" ) );
        type.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" ) ) );
        final WebElement hospital = driver.findElement( By.name( "hospital" ) );
        hospital.click();

        final WebElement date = driver.findElement( By.name( "date" ) );
        date.clear();
        date.sendKeys( dateString );
        date.click();

        final WebElement time = driver.findElement( By.name( "time" ) );
        time.clear();
        time.sendKeys( "9:30 AM" );

        expectedBhm = new BasicHealthMetrics();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "head" ) ) );
        final WebElement head = driver.findElement( By.name( "head" ) );
        head.clear();
        head.sendKeys( headString );
        try {
            expectedBhm.setHeadCircumference( Float.parseFloat( headString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement heightLength = driver.findElement( By.name( "height" ) );
        heightLength.clear();
        heightLength.sendKeys( lengthString );
        try {
            expectedBhm.setHeight( Float.parseFloat( lengthString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement weight = driver.findElement( By.name( "weight" ) );
        weight.clear();
        weight.sendKeys( weightString );
        try {
            expectedBhm.setWeight( Float.parseFloat( weightString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }
        try {
            final WebElement smoking = driver.findElement( By.cssSelector(
                    "input[value=\"" + HouseholdSmokingStatus.getName( Integer.parseInt( smokingStatus ) ) + "\"]" ) );
            smoking.click();
            expectedBhm.setHouseSmokingStatus( HouseholdSmokingStatus.parseValue( Integer.parseInt( smokingStatus ) ) );
        }
        catch ( final Exception e ) {
            /*
             * This means that the element wasn't found, which is expected if we
             * enter an invalid value (as one of the test cases does).
             * Intentionally ignoring.
             */
        }
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();
    }

    /**
     * Documents an office visit with specific information for patients between
     * 3 and 12
     *
     * @param dateString
     *            The current date.
     * @param weightString
     *            The weight of the patient.
     * @param heightString
     *            The height of the patient.
     * @param sys
     *            The systolic blood pressure of the patient.
     * @param dia
     *            The diastolic blood pressure of the patient.
     * @param smokingStatus
     *            The smoking status of the patient's household.
     * @param note
     *            The note that the doctor includes.
     * @throws InterruptedException
     */
    @When ( "^I fill in information on the office visit for patients of age 3 to 12 with date: (.+), weight: (.+), height: (.+), systolic blood pressure: (.+), diastolic blood pressure: (.+), household smoking status: (.+), and note: (.+)$" )
    public void documentOVWithSpecificInformation3To12 ( final String dateString, final String weightString,
            final String heightString, final String sys, final String dia, final String smokingStatus,
            final String note ) throws InterruptedException {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "notes" ) ) );
        final WebElement notes = driver.findElement( By.name( "notes" ) );
        notes.clear();
        notes.sendKeys( note );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "input[value=\"patient\"]" ) ) );
        final WebElement patient = driver.findElement( By.cssSelector( "input[value=\"patient\"]" ) );
        patient.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ) );
        final WebElement type = driver.findElement( By.name( "type" ) );
        type.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" ) ) );
        final WebElement hospital = driver.findElement( By.name( "hospital" ) );
        hospital.click();

        final WebElement date = driver.findElement( By.name( "date" ) );
        date.clear();
        date.sendKeys( dateString );
        date.click();

        final WebElement time = driver.findElement( By.name( "time" ) );
        time.clear();
        time.sendKeys( "9:30 AM" );

        expectedBhm = new BasicHealthMetrics();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "systolic" ) ) );
        final WebElement sysElem = driver.findElement( By.name( "systolic" ) );
        sysElem.clear();
        sysElem.sendKeys( sys );
        try {
            expectedBhm.setSystolic( Integer.parseInt( sys ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "diastolic" ) ) );
        final WebElement diaElem = driver.findElement( By.name( "diastolic" ) );
        diaElem.clear();
        diaElem.sendKeys( dia );
        try {
            expectedBhm.setDiastolic( Integer.parseInt( dia ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement heightLength = driver.findElement( By.name( "height" ) );
        heightLength.clear();
        heightLength.sendKeys( heightString );
        try {
            expectedBhm.setHeight( Float.parseFloat( heightString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement weight = driver.findElement( By.name( "weight" ) );
        weight.clear();
        weight.sendKeys( weightString );
        try {
            expectedBhm.setWeight( Float.parseFloat( weightString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }
        try {
            final WebElement smoking = driver.findElement( By.cssSelector(
                    "input[value=\"" + HouseholdSmokingStatus.getName( Integer.parseInt( smokingStatus ) ) + "\"]" ) );
            smoking.click();
            expectedBhm.setHouseSmokingStatus( HouseholdSmokingStatus.parseValue( Integer.parseInt( smokingStatus ) ) );
        }
        catch ( final Exception e ) {
            /*
             * This means that the element wasn't found, which is expected if we
             * enter an invalid value (as one of the test cases does).
             * Intentionally ignoring.
             */
        }
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();
    }

    /**
     * Documents an office visit with specific information for patients 12 and
     * over.
     *
     * @param dateString
     *            The current date.
     * @param weightString
     *            The weight of the patient.
     * @param heightString
     *            The height of the patient.
     * @param sys
     *            The systolic blood pressure of the patient.
     * @param dia
     *            The diastolic blood pressure of the patient.
     * @param houseSmoke
     *            The smoking status of the patient's household.
     * @param patientSmoke
     *            The smoking status of the patient.
     * @param hdl
     *            The patient's HDL levels.
     * @param ldl
     *            The patient's LDL levels.
     * @param tri
     *            The patient's triglycerides levels.
     * @param note
     *            The note entered by the HCP
     * @throws InterruptedException
     */
    @When ( "^I fill in information on the office visit for people 12 and over with date: (.+), weight: (.+), height: (.+), systolic blood pressure: (.+), diastolic blood pressure: (.+), household smoking status: (.+), patient smoking status: (.+), HDL cholesterol: (.+), LDL cholesterol: (.+), triglycerides: (.+), and note: (.+)$" )
    public void documentOVWithSpecificInformation12Over ( final String dateString, final String weightString,
            final String heightString, final String sys, final String dia, final String houseSmoke,
            final String patientSmoke, final String hdl, final String ldl, final String tri, final String note )
            throws InterruptedException {
        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "notes" ) ) );
        final WebElement notes = driver.findElement( By.name( "notes" ) );
        notes.clear();
        notes.sendKeys( note );

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "input[value=\"patient\"]" ) ) );
        final WebElement patient = driver.findElement( By.cssSelector( "input[value=\"patient\"]" ) );
        patient.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "type" ) ) );
        final WebElement type = driver.findElement( By.name( "type" ) );
        type.click();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "hospital" ) ) );
        final WebElement hospital = driver.findElement( By.name( "hospital" ) );
        hospital.click();

        final WebElement date = driver.findElement( By.name( "date" ) );
        date.clear();
        date.sendKeys( dateString );
        date.click();

        final WebElement time = driver.findElement( By.name( "time" ) );
        time.clear();
        time.sendKeys( "9:30 AM" );

        expectedBhm = new BasicHealthMetrics();

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "systolic" ) ) );
        final WebElement sysElem = driver.findElement( By.name( "systolic" ) );
        sysElem.clear();
        sysElem.sendKeys( sys );
        try {
            expectedBhm.setSystolic( Integer.parseInt( sys ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "diastolic" ) ) );
        final WebElement diaElem = driver.findElement( By.name( "diastolic" ) );
        diaElem.clear();
        diaElem.sendKeys( dia );
        try {
            expectedBhm.setDiastolic( Integer.parseInt( dia ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement heightLength = driver.findElement( By.name( "height" ) );
        heightLength.clear();
        heightLength.sendKeys( heightString );
        try {
            expectedBhm.setHeight( Float.parseFloat( heightString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        final WebElement weight = driver.findElement( By.name( "weight" ) );
        weight.clear();
        weight.sendKeys( weightString );
        try {
            expectedBhm.setWeight( Float.parseFloat( weightString ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }
        try {
            final WebElement smoking = driver.findElement( By.cssSelector(
                    "input[value=\"" + HouseholdSmokingStatus.getName( Integer.parseInt( houseSmoke ) ) + "\"]" ) );
            smoking.click();
            expectedBhm.setHouseSmokingStatus( HouseholdSmokingStatus.parseValue( Integer.parseInt( houseSmoke ) ) );
        }
        catch ( final Exception e ) {
            /*
             * This means that the element wasn't found, which is expected if we
             * enter an invalid value (as one of the test cases does).
             * Intentionally ignoring.
             */
        }
        try {
            final WebElement smoking = driver.findElement( By.cssSelector(
                    "input[value=\"" + PatientSmokingStatus.getName( Integer.parseInt( patientSmoke ) ) + "\"]" ) );
            smoking.click();
            expectedBhm.setPatientSmokingStatus( PatientSmokingStatus.parseValue( Integer.parseInt( patientSmoke ) ) );
        }
        catch ( final Exception e ) {
            /*
             * This means that the element wasn't found, which is expected if we
             * enter an invalid value (as one of the test cases does).
             * Intentionally ignoring.
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "hdl" ) ) );
        final WebElement hdlElem = driver.findElement( By.name( "hdl" ) );
        hdlElem.clear();
        hdlElem.sendKeys( hdl );
        try {
            expectedBhm.setHdl( Integer.parseInt( hdl ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "ldl" ) ) );
        final WebElement ldlElem = driver.findElement( By.name( "ldl" ) );
        ldlElem.clear();
        ldlElem.sendKeys( ldl );
        try {
            expectedBhm.setLdl( Integer.parseInt( ldl ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "tri" ) ) );
        final WebElement triElem = driver.findElement( By.name( "tri" ) );
        triElem.clear();
        triElem.sendKeys( tri );
        try {
            expectedBhm.setTri( Integer.parseInt( tri ) );
        }
        catch ( final IllegalArgumentException e ) {
            /*
             * This means that the test data provided was intentionally invalid,
             * which is okay
             */
        }

        wait.until( ExpectedConditions.visibilityOfElementLocated( By.name( "submit" ) ) );
        final WebElement submit = driver.findElement( By.name( "submit" ) );
        submit.click();
    }
}
