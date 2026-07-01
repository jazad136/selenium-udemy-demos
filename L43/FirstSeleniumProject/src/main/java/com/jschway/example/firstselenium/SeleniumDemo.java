package com.jschway.example.firstselenium;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDemo {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
//        findLoginPageElements(driver);
        findExceptionPageElements(driver);
        driver.quit();
    }
    private static void findExceptionPageElements(WebDriver driver) { 
        driver.get("https://practicetestautomation.com/practice-test-exception/");
        // find web element for the "Selenium WebDriver for beginners program" link
        // find WebElements for the input field using tag, class name, xpath, and CSS
        // Create a list of all button elements on the page using tag
        // identify both buttons using ID, name, css, and xpath for each
        WebElement lnkSeleniumWD4Beginners = driver.findElement(By.partialLinkText("Selenium WebDriver"));
        System.out.println(lnkSeleniumWD4Beginners.getText());
        
        // find WebElements for the input field using tag, class name, xpath, and CSS
        WebElement txtInputTag = driver.findElement(By.tagName("input"));
        WebElement txtInputClassName = driver.findElement(By.className("input-field"));
        WebElement txtInputXpath = driver.findElement(By.xpath("//input[@value='Pizza']"));
        WebElement txtInputCSSSelector = driver.findElement(By.cssSelector("input"));
        System.err.println(String.format("The Xpath Input Element says: \"%s\"",txtInputTag.getAttribute("value")));
        System.err.println(String.format("The class name Input Element says: \"%s\"", txtInputClassName.getAttribute("value")));
        System.err.println(String.format("The Xpath Input Element says: \"%s\"",txtInputXpath.getAttribute("value")));
        System.err.println(String.format("The CSS Input Element says: \"%s\"",txtInputCSSSelector.getAttribute("value")));
     
        List<WebElement> allButtons = driver.findElements(By.tagName("button"));
        allButtons.stream().map(e -> e.getAttribute("name")).collect(Collectors.joining(",", "The names of the buttons are: ", "(end)"));
        
        WebElement btnAddId = driver.findElement(By.id("add_btn"));
        WebElement btnAddName = driver.findElement(By.name("Add"));
        WebElement btnAddXpath = driver.findElement(By.xpath("//button[@name='Add']"));
        WebElement btnAddCSSSelector = driver.findElement(By.cssSelector("button[name=Add]"));
        
        WebElement btnEditId = driver.findElement(By.id("edit_btn"));
        WebElement btnEditName = driver.findElement(By.name("Edit"));
        WebElement btnEditXpath = driver.findElement(By.xpath("//button[@name='Edit']"));
        WebElement btnEditCSSSelector = driver.findElement(By.cssSelector("button[name=Edit]"));

//        WebElement btnSaveId = driver.findElement(By.id("save_btn"));
//        WebElement btnSaveName = driver.findElement(By.name("Save"));
//        WebElement btnSaveXpath = driver.findElement(By.xpath("//button[@name='Save']"));
//        WebElement btnSaveCSSSelector = driver.findElement(By.cssSelector("button[name=Save]"));
        
//        WebDriverWait btnAddId.wait
        
//        WebElement btnRemoveId = driver.findElement(By.id("remove_btn"));
//        WebElement btnRemoveName = driver.findElement(By.name("Remove"));
//        WebElement btnRemoveXpath = driver.findElement(By.xpath("//button[@name='Remove']"));
//        WebElement btnRemoveCSSSelector = driver.findElement(By.cssSelector("button[name=Remove]"));
        
        System.err.println(String.format("The Id Add Button says: \"%s\"",btnAddId.getText()));
        System.err.println(String.format("The Name Add Button says: \"%s\"", btnAddName.getText()));
        System.err.println(String.format("The Xpath Input Add Button says: \"%s\"",btnAddXpath.getText()));
        System.err.println(String.format("The CSS Add Button says: \"%s\"",btnAddCSSSelector.getText()));
        
        System.err.println(String.format("The Id Edit Button says: \"%s\"",btnEditId.getText()));
        System.err.println(String.format("The Name Edit Button says: \"%s\"", btnEditName.getText()));
        System.err.println(String.format("The Xpath Input Edit Button says: \"%s\"",btnEditXpath.getText()));
        System.err.println(String.format("The CSS Edit Button says: \"%s\"",btnEditCSSSelector.getText()));
        
//        System.err.println(String.format("The Id Save Button says: \"%s\"",btnSaveId.getText()));
//        System.err.println(String.format("The Name Save Button says: \"%s\"", btnSaveName.getText()));
//        System.err.println(String.format("The Xpath Input Save Button says: \"%s\"",btnSaveXpath.getText()));
//        System.err.println(String.format("The CSS Save Button says: \"%s\"",btnSaveCSSSelector.getText()));
        
//        System.err.println(String.format("The Id Remove Button says: \"%s\"",btnRemoveId.getText()));
//        System.err.println(String.format("The Name Remove Button says: \"%s\"", btnRemoveName.getText()));
//        System.err.println(String.format("The Xpath Input Remove Button says: \"%s\"",btnRemoveXpath.getText()));
//        System.err.println(String.format("The CSS Remove Button says: \"%s\"",btnRemoveCSSSelector.getText()));
     
    }
    private static void findLoginPageElements(WebDriver driver) {
        driver.get("https://practicetestautomation.com/practice-test-login/");
        try { 
            WebElement usernameInputField = driver.findElement(By.id("username"));
//            WebElement usernameInputFieldXpath = driver.findElement(By.xpath("//input[@id='username']"));
//            WebElement usernameInputFieldCss = driver.findElement(By.cssSelector("input[id='username']"));
            usernameInputField.sendKeys("anyUsername");
            WebElement passwordInputField = driver.findElement(By.name("password"));
            passwordInputField.sendKeys("anyPassword");
//            WebElement passwordInputFieldXpath = driver.findElement(By.xpath("//input[@name='password']"));
//            WebElement passwordInputFieldCss = driver.findElement(By.cssSelector("input[name='password']"));

            WebElement submitButton = driver.findElement(By.className("btn"));
            submitButton.isDisplayed();
            try{Thread.sleep(5000);}
            catch(InterruptedException e) { }
            submitButton.click();
//            WebElement submitButtonFieldXPath = driver.findElement(By.xpath("//button[@id='submit']"));
//            WebElement submitButtonFieldCss = driver.findElement(By.cssSelector("button[id='submit']"));
//            WebElement submitButtonFieldCss2 = driver.findElement(By.cssSelector("button[id='submit'][class='btn']"));
//            WebElement submitButtonFieldCss3 = driver.findElement(By.cssSelector("#submit.btn"));

//            List<WebElement> inputFields = driver.findElements(By.tagName("input"));
//            WebElement linkTextLocator = driver.findElement(By.linkText("Practice Test Automation."));
//            WebElement passwordFieldBelowUsername = driver.findElement(RelativeLocator.with(By.tagName("input")).below(By.id("username")));
//            WebElement privacyPolicyLink = driver.findElement(RelativeLocator.with(By.tagName("a")).toRightOf(By.partialLinkText("Test Automation")));
//            WebElement homeButton = driver.findElement(By.className("menu-item-home"));
        } catch(NoSuchElementException e) { 
            throw e;
        }
    }
    private static String chromeTest(String url) { 
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        String title = driver.getTitle();
        driver.quit();
        return title;
    }
    private static String firefoxTest(String url) { 
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get(url);
        String title = driver.getTitle();
        driver.quit();
        return title;
    }
}
