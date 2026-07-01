package com.practicetestautomtation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeLoginTests {
    
    @Test
    public void incorrectUsernameTest() { 
        // Open page
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        
        // Type username incorrectUser into Username field
        WebElement txtUsername = driver.findElement(By.id("username"));
        txtUsername.sendKeys("incorrectUser");
        
        // Type password Password123 into Password field
        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys("Password123");
        
        //  Push Submit button
        WebElement btnSubmit = driver.findElement(By.id("submit"));
        btnSubmit.click();
        
        //  Verify error message is displayed
        WebElement lblError = driver.findElement(By.id("error"));
//        String pageSource = driver.getPageSource();
        Assert.assertTrue(lblError.isDisplayed());
        
        //  Verify error message text is Your username is invalid!
        String actualErrorMessage = lblError.getText();
        String expectedErrorMessage = "Your username is invalid!";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        
        driver.quit();
    }
    
    @Test
    public void incorrectPasswordTest() { 
        // Open page
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        
        // Type username student into Username field
        WebElement txtUsername = driver.findElement(By.id("username"));
        txtUsername.sendKeys("student");
        
        // Type password incorrectPassword into Password field
        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys("incorrectPassword");
        
        // Push Submit button
        WebElement btnSubmit = driver.findElement(By.id("submit"));
        btnSubmit.click();
        
        // Verify error message is displayed
        WebElement lblError = driver.findElement(By.id("error"));
        Assert.assertTrue(lblError.isDisplayed());
        
        // Verify error message text is Your password is invalid!
        String actualErrorMessage = lblError.getText();
        String expectedErrorMessage = "Your password is invalid!";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        
        driver.quit();
    }
}
