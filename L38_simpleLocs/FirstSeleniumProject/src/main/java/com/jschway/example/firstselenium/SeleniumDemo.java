package com.jschway.example.firstselenium;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

public class SeleniumDemo {
    /**
     * Prints "Google" to the output
     */
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        // WebElementLocators will be added here.
        WebElement usernameInputField = driver.findElement(By.id("username"));
        WebElement passwordInputField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.className("btn"));
        List<WebElement> inputFields = driver.findElements(By.tagName("input"));
        WebElement linkTextLocator = driver.findElement(By.linkText("Practice Test Automation."));
        WebElement passwordFieldBelowUsername = driver.findElement(RelativeLocator.with(By.tagName("input")).below(By.id("username")));
        WebElement privacyPolicyLink = driver.findElement(RelativeLocator.with(By.tagName("a")).toRightOf(By.partialLinkText("Test Automation")));
        driver.quit();
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
