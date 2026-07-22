package com.practicetestautomation.tests.login;

import com.practicetestautomation.dataprovider.TestDataProviders;
import com.practicetestautomation.pageobjects.LoginPage;
import com.practicetestautomation.pageobjects.SuccessfulLoginPage;
import com.practicetestautomation.tests.BaseTest;
import com.practicetestautomation.testsetup.ExtentReportManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    
    @Test(groups = {"positive", "regression", "smoke"}, 
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderLoginSuite")
    public void testLoginFunctionality(String testName) {
        // Open page
        info("Starting " + testName);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        
        // Type username student into Username field
        // Type password Password123 into Password field
        // Push Submit button
        
        info("Type username, password, and click submit button");
        SuccessfulLoginPage successfulLoginPage = loginPage.executeLogin("student", "Password123");
        successfulLoginPage.load();

        // Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Logged In Successfully";
        String pageSource = successfulLoginPage.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
        Assert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
        info("Verify the login functionality");
        pass("Test passed...");
    }
//    @Parameters({"username", "password", "expectedErrorMessage", "testName"})
    @Test(groups = {"negative", "regression"},
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderLoginSuite")
    public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
        // Open page
        info("Starting negativeLoginTest");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        
        // Type username incorrectUser into Username field        
        // Type password Password123 into Password field
        //  Push Submit button
        infoFormat("Type username: %s, password: %s", username, password);
        info("Click submit button");
        loginPage.executeLogin(username, password);
        
        //  Verify error message is displayed
        //  Verify error message text is Your username is
        Assert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
        pass("Test passed...");
    }
}
