package com.practicetestautomtation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
        usernameInput.sendKeys("Password123");
        // Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        // Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        
        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        // Verify button Log out is displayed on the new page
        driver.quit();
    }
}
