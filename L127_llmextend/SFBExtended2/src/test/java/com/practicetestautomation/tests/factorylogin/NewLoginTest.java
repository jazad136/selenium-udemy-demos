/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practicetestautomation.tests.factorylogin;

import com.aventstack.extentreports.Status;
import com.practicetestautomation.dataprovider.TestDataProviders;
import com.practicetestautomation.pageobjects.LoginPage;
import com.practicetestautomation.pageobjects.SuccessfulLoginPage;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

/**
 *
 * @author JonathanSaddler
 */
public class NewLoginTest extends LoginTestFactory {
    public NewLoginTest() { } 
    @Factory(dataProviderClass = LoginTestFactory.class, dataProvider="testFactoryDpMethod") 
    public NewLoginTest(String browser, String testNamePrepend) { 
        super(browser, testNamePrepend);
    }
    
    @Test
    public void testLoginFunctionality() {
        // Open page
        info("Starting " + getFullTestName());
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
        softAssert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
        info("Verify the login functionality completed");
        softAssert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
        
        softAssert.assertAll();
    }
    
    @Test(groups = {"negative", "regression"},
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderLoginSuite")
    public void negativeLoginTest(
            String username, 
            String password, 
            String expectedErrorMessage,
            String testEnd) {
        this.testEnd = "";
        String fullTestName = getFullTestName();
        // Open page
        info("Starting " + getFullTestName());
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
        testReport.log(Status.INFO, "Test Case Name : " + fullTestName);
        testReport.pass("Reached the end.");
    }
}
