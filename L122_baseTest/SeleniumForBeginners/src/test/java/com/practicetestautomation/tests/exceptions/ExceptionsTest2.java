package com.practicetestautomation.tests.exceptions;

import com.practicetestautomation.pageobjects.ExceptionsPage;
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

public class ExceptionsTest2 {
    
    private WebDriver driver;
    private Logger logger;
    
    @Parameters("browser")
    @BeforeMethod(alwaysRun=true)
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(ExceptionsTest2.class.getName());
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
//        wait = setupWait(driver);
   // Open page
//        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
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
        exceptionsPage.loadRow2();
        // Assert that row 2 is loaded
        Assert.assertTrue(exceptionsPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testTimeoutException() { 
        // open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for 3 seconds for the second input field to be displayed
        excPage.loadRow2();
        Assert.assertTrue(excPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testElementNotInteractibleException() { 
        // Open Page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for the second row to load
        excPage.loadRow2();
        // Type text into the second input field
        String inputText = "Hamburger";
        excPage.enterRow2Text(inputText);
        // Push Save button using locator By.name(“Save”)
        excPage.saveRowTwo();
        // Verify text saved
        String expectedText = "Row 2 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText,  "Message is not displayed.");
        // This page contains two elements with attribute name=”Save”.
        // The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }
    
    @Test
    public void testInvalidElementStateException() {
        
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); // Open page
        excPage.clickEdit(); // click Edit Button
        excPage.clearRow1InputField(); // Clear input field
        // The input field is disabled. Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.
        // If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.

        // addl test: verify text field is empty
        Assert.assertEquals(excPage.getRow1InputFieldText(), "", "Row 1 input field is not empty");
        // Type text into the input field
        excPage.enterFoodInRow1("Sushi");
        // Verify Text Changed
        String text1 = excPage.getRow1InputFieldText();
        Assert.assertEquals(text1,"Sushi", "Row 1 input field text is not Hamburger");
    }
    @Test
    public void testStaleElementException() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        // Find the instructions text element
//        WebElement instructionsText = driver.findElement(By.id("instructions"));
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); // Open page
        Assert.assertTrue(excPage.isInstructionsVisible(), "Instructions text is not displayed");
        // Push add button
        excPage.clickAdd();
        // Verify instruction text element is no longer displayed
        Assert.assertTrue(excPage.isInstructionsInvisibleAfterWait(), "Instructions text is still displayed after edit.");
    }
}
