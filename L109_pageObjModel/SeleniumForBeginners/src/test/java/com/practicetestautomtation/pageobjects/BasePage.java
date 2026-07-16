package com.practicetestautomtation.pageobjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
    protected void visit(String url) { driver.get(url); }
    
    public WebElement waitForElement(By locator) { 
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    private WebElement waitForElementByDuration(By locator, Duration byDuration) {
        WebDriverWait customWait = new WebDriverWait(driver, byDuration);
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /** 
     * If timeout is reached before element identified by locator is 
     * displayed, returns false, 
     * otherwise, returns true. 
     * @param locator the locator to use to find element
     * @return False - if the element could not be found in the timeout limit or is not displayed. 
     *              True if the element could be found and is displayed.
     */
    public boolean waitForIsDisplayed(By locator) { 
        try { 
            waitForElement(locator);
            return true;
        } catch(TimeoutException te) { 
            return false;
        }
    }
    public boolean waitForIsHidden(By locator) { 
        try { 
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;   
        } catch(TimeoutException te) { 
            return false;
        }
    }
    public boolean waitForIsInvisible(By locator) { 
        try { 
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch(TimeoutException te) { 
            return false;
        }
    }
    public boolean waitForIsInvisible(By locator, int durationSeconds) { 
        try { 
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(durationSeconds));
            return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch(TimeoutException te) { 
            return false;
        }
    }
    public boolean waitForIsDisplayed(By locator, int durationSeconds) { 
        try { 
            waitForElementByDuration(locator, Duration.ofSeconds(durationSeconds));
            return true;
        } catch(TimeoutException te) { 
            return false;
        }
    }
    protected boolean isDisplayed(By locator) { 
        try {
            return driver.findElement(locator).isDisplayed();
        } catch(NoSuchElementException e) { 
            return false;
        }
    }
}
