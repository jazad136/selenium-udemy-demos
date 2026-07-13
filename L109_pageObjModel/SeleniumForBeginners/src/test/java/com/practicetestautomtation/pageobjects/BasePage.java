package com.practicetestautomtation.pageobjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) { 
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public String getCurrentUrl() { return driver.getCurrentUrl(); }
    public String getPageSource() { return driver.getPageSource(); } 
    
    public WebElement waitForElement(By locator) { 
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected boolean isDisplayed(By locator) { 
        try {
            return driver.findElement(locator).isDisplayed();
        } catch(NoSuchElementException e) { 
            return false;
        }
    }
}
