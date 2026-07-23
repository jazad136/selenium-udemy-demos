/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomation.tests.factorylogin;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.practicetestautomation.testsetup.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

/**
 *
 * @author JonathanSaddler
 */
public abstract class FactoryBaseTest implements BaseTestInterface {
    protected WebDriver driver;
    private final String browser;
    private final String testNamePrepend;   
    private String testNameSuffix;
    protected ExtentReports extent;
    protected ExtentTest testReport;
    protected SoftAssert softAssert;
    public FactoryBaseTest() { browser = ""; testNamePrepend = "";} 
    public FactoryBaseTest(String browser, String testNamePrepend) { 
        this.browser = browser;
        this.extent = ExtentReportManager.getReporter();
        this.testNamePrepend = testNamePrepend != null && !testNamePrepend.equals("testNamePrepend") ? testNamePrepend : "";
        this.testNameSuffix = "";
    }
    
    public void setTestNameSuffix(String testName) { 
        this.testNameSuffix = testName;
    }
    @BeforeMethod(alwaysRun=true)
    public void setUp(ITestResult res) {
        switch (browser.toLowerCase()) {
            case "chrome": 
                driver = new ChromeDriver();
     break; case "firefox":
                driver = new FirefoxDriver();
     break; default: 
            driver = new ChromeDriver();
     break;
        }
        extent = ExtentReportManager.getReporter();
        
        softAssert = new SoftAssert();
        testReport = extent.createTest(getFullTestName());
        res.setAttribute("reporterObject", testReport);
    }
    public String getTestNameSuffix() {
        return testNameSuffix;
    }
    
    public String getTestNamePrepend() {
        return !testNamePrepend.isBlank() ? testNamePrepend + " " : ""; 
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
}
