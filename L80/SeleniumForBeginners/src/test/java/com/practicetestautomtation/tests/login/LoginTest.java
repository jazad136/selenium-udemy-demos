package com.practicetestautomtation.tests.login;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
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
    private Wait<WebDriver> wait;
    
    @Parameters("browser")
    @BeforeMethod(alwaysRun=true)
    public void setUp(@Optional("chrome") String browser) {
        System.err.println("Running test in " + browser);
        switch (browser.toLowerCase()) {
            case "chrome": 
                driver = new ChromeDriver();
     break; case "firefox":
                driver = new FirefoxDriver();
     break; default: 
            System.err.println("Configuration for " + browser + " is missing, so running tests in Chrome by default.");
            driver = new ChromeDriver();
     break;
        }
        wait = setupWait(driver);
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }
    
    @AfterMethod(alwaysRun=true)
    public void tearDown() { 
        driver.quit();
    }
    
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality() {
        // Open page
        
        // Type username student into Username field
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("student");

        // Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Password123");

        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        try { Thread.sleep(2000); } 
        catch(InterruptedException e) { }
       
        // Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectedUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Logged In Successfully";
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
        WebElement logOutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logOutButton.isDisplayed());
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
        // Open page
        
//        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.edge.driver","src/main/resources/msedgedriver.exe");
        
        // Type username incorrectUser into Username field
        WebElement txtUsername = driver.findElement(By.id("username"));
        txtUsername.sendKeys(username);
        
        // Type password Password123 into Password field
        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys(password);
        
        //  Push Submit button
        WebElement btnSubmit = driver.findElement(By.id("submit"));
        btnSubmit.click();
        
        //  Verify error message is displayed
        WebElement lblError = driver.findElement(By.id("error"));
//        String pageSource = driver.getPageSource();
        wait.until(ExpectedConditions.visibilityOf(lblError));
        Assert.assertTrue(lblError.isDisplayed());
        
        //  Verify error message text is Your username is invalid!
        String actualErrorMessage = lblError.getText();
        
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
