package com.practicetestautomtation.tests.exceptions;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTest {
    
    private WebDriver driver;
    private Wait<WebDriver> wait;
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
        addButton.click();
//        try { Thread.sleep(7000); } 
//        catch(InterruptedException e) { }
        
        // Verify Row 2 input field is displayed
        // throws no such element exception
//        Assert.assertThrows(NoSuchElementException.class, () ->);
        WebElement row2Input = driver.findElement(By.xpath("//div[@id='row2']/input")); 
        Assert.assertTrue(row2Input.isDisplayed(), "Row 2 input field is not displayed.");
    }
    
    public Wait<WebDriver> setupWait(WebDriver driver) { 
        return new FluentWait<>(driver)
                .withTimeout(Duration.of(10000, ChronoUnit.MILLIS))
                .pollingEvery(Duration.of(2000, ChronoUnit.MILLIS))
                .ignoring(NoSuchElementException.class);
    }
}
