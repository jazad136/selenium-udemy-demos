package com.jschway.example.firstselenium;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

public class SeleniumDemo {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        driver.get("https://practicetestautomation.com/practice-test-login/");
        // WebElementLocators will be added here.
        try { 
            WebElement usernameInputField = driver.findElement(By.id("username"));
            WebElement usernameInputFieldXpath = driver.findElement(By.xpath("//input[@id='username']"));
            WebElement usernameInputFieldCss = driver.findElement(By.cssSelector("input[id='username']"));

            WebElement passwordInputField = driver.findElement(By.name("password"));
            WebElement passwordInputFieldXpath = driver.findElement(By.xpath("//input[@name='password']"));
            WebElement passwordInputFieldCss = driver.findElement(By.cssSelector("input[name='password']"));

            WebElement submitButton = driver.findElement(By.className("btn"));
            WebElement submitButtonFieldXPath = driver.findElement(By.xpath("//button[@id='submit']"));
            WebElement submitButtonFieldCss = driver.findElement(By.cssSelector("button[id='submit']"));
            WebElement submitButtonFieldCss2 = driver.findElement(By.cssSelector("button[id='submit'][class='btn']"));
            WebElement submitButtonFieldCss3 = driver.findElement(By.cssSelector("#submit.btn"));

            List<WebElement> inputFields = driver.findElements(By.tagName("input"));
            WebElement linkTextLocator = driver.findElement(By.linkText("Practice Test Automation."));
            WebElement passwordFieldBelowUsername = driver.findElement(RelativeLocator.with(By.tagName("input")).below(By.id("username")));
            WebElement privacyPolicyLink = driver.findElement(RelativeLocator.with(By.tagName("a")).toRightOf(By.partialLinkText("Test Automation")));
        } catch(NoSuchElementException e) { 
            throw e;
        }
        finally{
            driver.quit();
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
