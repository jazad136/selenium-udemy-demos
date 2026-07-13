package com.practicetestautomtation.tests.login;

import com.practicetestautomtation.pageobjects.LoginPage;
import com.practicetestautomtation.pageobjects.SuccessfulLoginPage;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest {
    
    private WebDriver driver;
    private Logger logger;
    
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
    
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality() {
        // Open page
        logger.info("Starting testLoginFunctionality");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        
        // Type username student into Username field
        // Type password Password123 into Password field
        // Push Submit button
//        WebElement usernameInput = driver.findElement(By.id("username"));
//        WebElement passwordInput = driver.findElement(By.id("password"));
//        WebElement submitButton = driver.findElement(By.id("submit"));
//        logger.info("Type password");
//        logger.info("Type username");
//        usernameInput.sendKeys("student");
//        passwordInput.sendKeys("Password123");
//        logger.info("Click Submit button");
//        submitButton.click();
        SuccessfulLoginPage successfulLoginPage = loginPage.executeLogin("student", "Password123");
        successfulLoginPage.load();

//        try { Thread.sleep(2000); } 
//        catch(InterruptedException e) { }
        logger.info("Verify the login functionality");
        // Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectedUrl = "https://practicetestautomation.com/logged-in-successfully/";
//        String actualUrl = driver.getCurrentUrl();
        String actualUrl = successfulLoginPage.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Logged In Successfully";
//        String pageSource = driver.getPageSource();
        String pageSource = successfulLoginPage.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
//        WebElement logOutButton = driver.findElement(By.linkText("Log out"));
//        Assert.assertTrue(logOutButton.isDisplayed());
        Assert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
    }
    
    public Wait<WebDriver> setupWait(WebDriver driver) { 
        return new FluentWait<>(driver)
                .withTimeout(Duration.of(10000, ChronoUnit.MILLIS))
                .pollingEvery(Duration.of(2000, ChronoUnit.MILLIS))
                .ignoring(NoSuchElementException.class);
    }
 
    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) { 
        logger.info("Starting negativeLoginTest");
        // Open page
        
        // Type username incorrectUser into Username field
        WebElement txtUsername = driver.findElement(By.id("username"));
        logger.info(String.format("Typing username: %s", username));
        txtUsername.sendKeys(username);
        
        // Type password Password123 into Password field
        logger.info("Typing password");
        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys(password);
        
        //  Push Submit button
        logger.info("Clicking submit button");
        WebElement btnSubmit = driver.findElement(By.id("submit"));
        btnSubmit.click();
        
        //  Verify error message is displayed
        WebElement lblError = driver.findElement(By.id("error"));
        
        logger.info(String.format("Verify the expected error message: %s", expectedErrorMessage));
//        wait.until(ExpectedConditions.visibilityOf(lblError));
        Assert.assertTrue(lblError.isDisplayed());
        
        //  Verify error message text is Your username is invalid!
        String actualErrorMessage = lblError.getText();
        
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }   
}
