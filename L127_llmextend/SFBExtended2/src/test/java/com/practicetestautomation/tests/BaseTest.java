package com.practicetestautomation.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.practicetestautomation.tests.exceptions.ExceptionsTest;
import com.practicetestautomation.testsetup.ExtentReportManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    
    protected WebDriver driver;
    protected Logger logger;
    protected ExtentReports extent;
    protected ExtentTest testReport;
    private String testNameAppend;
    protected SoftAssert softAssert;
    
    @Parameters({"browser", "testName", "testAppend"})
    @BeforeMethod(alwaysRun=true)
    public void setUp(
            @Optional("chrome") String browser, 
            @Optional("testName") String testName, 
            @Optional("testAppend") String testAppend, 
            ITestResult testResult) {
        logger = Logger.getLogger(ExceptionsTest.class.getName());
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
        extent = ExtentReportManager.getReporter();
        testNameAppend = testAppend != null && !testAppend.equals("testAppend") ? testAppend : "";
        softAssert = new SoftAssert();
    }
    
    @AfterMethod(alwaysRun=true)
    public void tearDown() { 
        driver.quit();
        extent.flush();
    }
    public void info(String msg) {
        System.out.println(msg);
        testReport.info(msg);
    }
    public void infoFormat(String formatMsg, Object... formatObjs) {
        System.out.printf(formatMsg + "\n", formatObjs);
        testReport.info(String.format(formatMsg + "\n", formatObjs));
    }
    public void pass(String msg) { 
        System.out.println(msg);
        testReport.pass(msg);
    }
    public void fail(String msg) { 
        System.out.println(msg);
        testReport.fail(msg);
    }
    public void skip(String msg) { 
        System.out.println(msg);
        testReport.skip(msg);
    }
    
    public String getTestNameAppend() { 
        return !testNameAppend.isBlank() ? testNameAppend + " " : ""; 
    }
    protected String createFullTestName(String inputTestName) { 
        String endTestName = inputTestName != null && !inputTestName.equals("testName") ? inputTestName : "Login Test";
        return getTestNameAppend() + endTestName;
    }
}
