package com.practicetestautomtation.tests.login;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveLoginTests {
    @Test
    public void testLoginFunctionality() {
        // Open page
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

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
//         Wait<WebDriver> wait = new FluentWait<>(driver)
//                .withTimeout(Duration.of(10000, ChronoUnit.MILLIS))
//                .pollingEvery(Duration.of(2000, ChronoUnit.MILLIS))
//                .ignoring(NoSuchElementException.class);
//        wait.until(ExpectedConditions.visibilityOf(logOutButton));
        Assert.assertTrue(logOutButton.isDisplayed());
        
        driver.quit();
    }
}
