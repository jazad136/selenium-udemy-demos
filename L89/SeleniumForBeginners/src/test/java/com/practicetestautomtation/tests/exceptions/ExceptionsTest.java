package com.practicetestautomtation.tests.exceptions;

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

public class ExceptionsTest {
    
    private WebDriver driver;
    private Logger logger;
    
    @Parameters("browser")
    @BeforeMethod(alwaysRun=true)
    public void setUp(@Optional("chrome") String browser) {
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
//        wait = setupWait(driver);
   // Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }
    
    @AfterMethod(alwaysRun=true)
    public void tearDown() { 
        driver.quit();
        logger.info("Browser is closed");
    }
    
    @Test
    public void testNoSuchElementException() { 
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        addButton.click();
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        // Verify Row 2 input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testTimeoutException() { 
        
        // Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        
        // Wait for 3 seconds for the second input field to be displayed
        addButton.click();
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        // Verify second input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed.");
        // The second row shows up after about 5 seconds, so a 3-second timeout is not enough. 
        //That’s why we will get TimeoutException while executing steps in the above test case.
    }
    @Test
    public void testElementNotInteractibleException() { 
        WebElement addButton = driver.findElement(By.id("add_btn"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        
        // Click Add button
        addButton.click();
        // Wait for the second row to load
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        
        // Type text into the second input field
        String inputText = "Hamburger";
        row2InputField.sendKeys(inputText);
        // Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.name("Save"));
        saveButton.click();
        // Verify text saved
        String expectedText = "Row 2 was saved";
        WebElement confirmationLabel = driver.findElement(By.id("confirmation"));
        Assert.assertEquals(confirmationLabel.getText(), expectedText, "Row 2 was not saved. Label did not appear.");

        // This page contains two elements with attribute name=”Save”.
        // The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }
}
