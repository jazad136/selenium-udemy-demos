/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomtation.pageobjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExceptionsPage extends BasePage {
    By addButtonLocator = By.id("add_btn");
    By instructionsLocator = By.id("instructions");
    By editButtonLocator = By.id("edit_btn");
    By row1InputFieldLocator = By.xpath("//div[@id='row1']/input");
    By row1SaveButtonLocator = By.xpath("//div[@id='row1']/button[@name='Save']");
    By row2SaveButtonLocator = By.xpath("//div[@id='row2']/button[@name='Save']");
    By row2InputFieldLocator = By.xpath("//div[@id='row2']/input");
    By confirmationLocator = By.id("confirmation");
    WebDriverWait timeoutWait;
    
    public ExceptionsPage(WebDriver driver) { 
        super(driver);
        timeoutWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }
    
    public void visit() { super.visit("https://practicetestautomation.com/practice-test-exceptions/"); }
    
    public void clickAdd() {driver.findElement(addButtonLocator).click(); }
    public boolean isRow2FieldDisplayed() { return isDisplayed(row2InputFieldLocator); } 
    public boolean isRowTwoDisplayedAfterWait() { return waitForIsDisplayed(row2InputFieldLocator); }
    public boolean isRowTwoDisplayedInSeconds(int secondsDuration) { return waitForIsDisplayed(row2InputFieldLocator, secondsDuration); }

    public void enterFoodInRow1(String text) { driver.findElement(row1InputFieldLocator).sendKeys(text); }
    public void enterFoodInRowOne(String text) { 
        WebElement row1Input = driver.findElement(row1InputFieldLocator);
        row1Input.clear();
        row1Input.sendKeys(text);
    }
    
    public void enterRow2Text(String text) {driver.findElement(row2InputFieldLocator).sendKeys(text);}
    public void enterFoodInRow2(String text) { driver.findElement(row2InputFieldLocator).sendKeys(text); }
    public void enterRow1Text(String text) { driver.findElement(row1InputFieldLocator).sendKeys(text); }

    public WebElement waitForElement(boolean use3SecTimeout, By locator) { 
        if(use3SecTimeout)  
//            return waitForElementByDuration(locator, Duration.ofSeconds(3)); 
            return timeoutWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        else
            return waitForElement(locator);
    }
    public void loadRow2() {
        waitForElement(row2InputFieldLocator);
    }
    public void loadRow2In3Seconds() {
        waitForElement(true, row2InputFieldLocator);
    }
    
    public void saveRowOne() { driver.findElement(row1SaveButtonLocator).click(); }
    public void saveRowTwo() { driver.findElement(row2SaveButtonLocator).click(); } 
    

    public String getSuccessMessage() { 
        return waitForElement(confirmationLocator).getText();
    }

    public void clickEdit() { driver.findElement(editButtonLocator).click(); }

    public void clearRow1InputField() { driver.findElement(row1InputFieldLocator).clear(); }
    public void clearRow2InputField() { driver.findElement(row2InputFieldLocator).clear(); }

    public String getRow1InputFieldText() { return driver.findElement(row1InputFieldLocator).getAttribute("value"); }

    public boolean isInstructionsVisible() { return driver.findElement(instructionsLocator).isDisplayed(); }
    public boolean isInstructionsInvisibleAfterWait() { return waitForIsInvisible(instructionsLocator); }
    public boolean isInstructionsHiddenAfterWait() { return waitForIsHidden(instructionsLocator); }


}
