package com.practicetestautomation.tests.login;

import com.aventstack.extentreports.Status;
import com.practicetestautomation.dataprovider.TestDataProviders;
import com.practicetestautomation.pageobjects.LoginPage;
import com.practicetestautomation.pageobjects.SuccessfulLoginPage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    @Test(groups = {"positive", "regression", "smoke"}, 
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderLoginSuite")
    public void testLoginFunctionality(String testName) {
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
//        testResult.setAttribute("reporterObject", testReport);
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
        softAssert.assertTrue(pageSource.contains(expectedMessage));
        
        // Verify button Log out is displayed on the new page
        info("Verify the login functionality completed");
        softAssert.assertTrue(successfulLoginPage.isLogoutButtonDisplayed());
        
        softAssert.assertAll();
        testReport.log(Status.INFO, "Test Case Name : " + fullTestName);
        testReport.pass("Reached the end.");
    }
//    @Parameters({"username", "password", "expectedErrorMessage", "testName"})
    @Test(groups = {"negative", "regression"},
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderLoginSuite")
    public void negativeLoginTest(
            String username, 
            String password, 
            String expectedErrorMessage,
            String testName) {
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
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
        testReport.log(Status.INFO, "Test Case Name : " + fullTestName);
        testReport.pass("Reached the end.");
    }
    private String createFullTestName(String inputTestName) { 
        String endTestName = inputTestName != null && !inputTestName.equals("testName") ? inputTestName : "Login Test";
        return getTestNameAppend() + endTestName;
    }
}
