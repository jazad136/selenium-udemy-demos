package com.practicetestautomtation.tests.exceptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.practicetestautomtation.pageobjects.ExceptionsPage;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTest3 {
    
    private WebDriver driver;
    private Logger logger;
    private ExtentReports extent;
    private ExtentTest excReport;
    
    @Parameters("browser")
    @BeforeMethod(alwaysRun=true)
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(ExceptionsTest3.class.getName());
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
    
    @Test
    public void testNoSuchElementException() { 
        // Open Page
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        // Click Add button
        exceptionsPage.clickAdd();
        // Wait for row 2 to load
        // Assert that row 2 is loaded
        Assert.assertTrue(exceptionsPage.isRowTwoDisplayedAfterWait(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testTimeoutException() { 
        // open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
//         Wait for 3 seconds for the second input field to be displayed
//        excPage.loadRow2In3Seconds();
        Assert.assertTrue(excPage.isRowTwoDisplayedAfterWait(), "Row 2 input field was not displayed in 3 seconds.");
    }
    
    @Test
    public void testElementNotInteractibleException() { 
        // Open Page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for the second row to load
        excPage.isRowTwoDisplayedAfterWait();
        // Type text into the second input field
        String inputText = "Hamburger";
        excPage.enterFoodInRow2(inputText);
        // Push Save button using locator By.name("Save")
        excPage.saveRowTwo();
        // Verify text saved
        String expectedText = "Row 2 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText, "Message is not displayed.");
        // This page contains two elements with attribute name=”Save”.
        // The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }
    
    @Test
    public void testInvalidElementStateException() {
        // Open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // click Edit Button
        excPage.clickEdit();
        // The input field is disabled. Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.
        // If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.
        // Type text into the input field
        
        // Clear input field
        excPage.enterFoodInRowOne("Sushi");
        // Click Save Button? 
        excPage.saveRowOne();
        // Verify text was saved?
        String expectedText = "Row 1 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText, "Row 1 was not saved. Label did not appear.");
    }
    @Test
    public void testStaleElementException() {
        // Open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Push add button
        excPage.clickAdd();
        // Verify instruction text element is no longer displayed
        Assert.assertTrue(excPage.isInstructionsInvisibleAfterWait(), "Instructions text is still displayed");
    }
}
