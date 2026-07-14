package com.practicetestautomtation.tests.login;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.practicetestautomtation.pageobjects.LoginPage;
import com.practicetestautomtation.pageobjects.SuccessfulLoginPage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest {
    
    private WebDriver driver;
    private Logger logger;
    ExtentReports extent;
    
    @BeforeClass(alwaysRun=true)
    public void setUpSuite()  { 
        extent = new ExtentReports();
//        final File CONF = new File("src/test/resources/ExtentConfigs/extentConfig.xml");
        final File CONF = new File("src/test/resources/ExtentConfigs/extentConfig.xml");
        ExtentSparkReporter spark = new ExtentSparkReporter("xml-config-report.html");
        try { 
            spark.loadXMLConfig(CONF);
        } catch(IOException e) { 
            throw new RuntimeException("Could not set up suite with XML configuration at src/main/resources/extentConfig.xml");
        }
        extent.attachReporter(spark);
    }
    
    @Parameters("browser")
    @BeforeMethod(alwaysRun=true)
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(LoginTest.class.getName());
        logger.setLevel(Level.INFO);
        
        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()) {
            case "chrome": 
                driver = new ChromeDriver();
     break; case "firefox":
                driver = new FirefoxDriver();
     break; default: 
            logger.warning("Configuration for " + browser + " is missing, so running tests in Chrome by default.");
            driver = new ChromeDriver();
     break;
        }
    }
    
    @AfterMethod(alwaysRun=true)
    public void tearDown() { 
        driver.quit();
        logger.info("Browser is closed");
    }
//    @AfterSuite(alwaysRun=true)
//    public void tearDownAll() {
//        
//    }
    
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality() {
        ExtentTest loginReport = extent.createTest("testLoginFunctionality");
        // Open page
        loginReport.info("Starting testLoginFunctionality");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        
        // Type username student into Username field
        // Type password Password123 into Password field
        // Push Submit button
        
        loginReport.info("Type username, password, and click submit button");
        SuccessfulLoginPage successfulLoginPage = loginPage.executeLogin("student", "Password123");
        successfulLoginPage.load();

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Logged In Successfully";
        String pageSource = successfulLoginPage.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
        Assert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
        loginReport.info("Verify the login functionality");
        loginReport.pass("Test passed...");
        extent.flush();
    }
    
//    public Wait<WebDriver> setupWait(WebDriver driver) { 
//        return new FluentWait<>(driver)
//                .withTimeout(Duration.of(10000, ChronoUnit.MILLIS))
//                .pollingEvery(Duration.of(2000, ChronoUnit.MILLIS))
//                .ignoring(NoSuchElementException.class);
//    }
 
    @Parameters({"username", "password", "expectedErrorMessage", "testName"})
    @Test(groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage, String testName) {
        ExtentTest loginReport = extent.createTest("negativeLoginTest-"+testName);
        // Open page
        loginReport.info("Starting negativeLoginTest");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        
        // Type username incorrectUser into Username field        
        // Type password Password123 into Password field
        //  Push Submit button
        loginReport.info("Type username, password, and click submit button");
        loginPage.executeLogin(username, password);
        
        //  Verify error message is displayed
        //  Verify error message text is Your username is
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
        loginReport.pass("Test passed...");
        extent.flush();
    }
}
