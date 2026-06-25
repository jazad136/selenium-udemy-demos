package com.jschway.example.firstselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDemo {
    /**
     * Prints "Google" to the output
     */
    public static void main(String[] args) {
        String chromeTitle = chromeTest("https://www.google.com/");
        System.out.println(chromeTitle);
        String firefoxTitle = firefoxTest("https://www.facebook.com");
        System.out.println(firefoxTitle);
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
