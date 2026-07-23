package com.practicetestautomation.tests.exceptions;

import com.aventstack.extentreports.Status;
import com.practicetestautomation.dataprovider.TestDataProviders;
import com.practicetestautomation.pageobjects.ExceptionsPage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionsTest2 extends BaseTest {
    @Test(dataProviderClass = TestDataProviders.class, 
          dataProvider = "dataProviderExceptionSuite")
    public void testNoSuchElementException(String testName) { 
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
        
        info("Open Page");
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        
        info("Click Add button");
        exceptionsPage.clickAdd();
        info("Wait for row 2 to load");
        // Wait for row 2 to load
        exceptionsPage.loadRow2();
        // Assert that row 2 is loaded
        softAssert.assertTrue(exceptionsPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
        infoFormat("Test Case Name %s ", fullTestName);
        softAssert.assertAll();
    }
    
    @Test(dataProviderClass = TestDataProviders.class, 
          dataProvider = "dataProviderExceptionSuite")
    public void testElementNotInteractibleException(String testName) { 
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
        info("Open page");
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        info("Click Add button");
        excPage.clickAdd();
        info("Wait for row 2 to load");
        excPage.loadRow2();
        info("Type text into 2nd input field");
        String inputText = "Hamburger";
        excPage.enterRow2Text(inputText);
        info("Click Save");
        excPage.saveRowTwo();
        info("Verify the text was saved (that success message shows)");
        String expectedText = "Row 2 was saved";
        softAssert.assertEquals(excPage.getSuccessMessage(), expectedText,  "Message is not displayed.");
        // The first one is invisible. 
        // we're trying to avoid ElementNotVisibleException
        // So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
        infoFormat("Test Case Name %s ", fullTestName);
        softAssert.assertAll();
    }
    
    @Test(dataProviderClass = TestDataProviders.class, 
          dataProvider = "dataProviderExceptionSuite")
    public void testTimeoutException(String testName) { 
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
        
        info("Open Page");
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        info("Click Add button");
        excPage.clickAdd();
        // Wait for 3 seconds for the second input field to be displayed
        excPage.loadRow2();
        softAssert.assertTrue(excPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
        infoFormat("Test Case Name %s ", fullTestName);
        softAssert.assertAll();
    }
    
    
    @Test(dataProviderClass = TestDataProviders.class, 
          dataProvider = "dataProviderExceptionSuite")
    public void testInvalidElementStateException(String testName) {
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
        info("Open page");
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); 
        info("Click Edit button");
        excPage.clickEdit(); // click Edit Button
        info("Clear Row 1 Input Field");
        excPage.clearRow1InputField(); // Clear input field
        // The input field is disabled. 
        // We're trying to avoid InvalidElementStateException Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.
        // If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.
        
        // addl test: verify text field is empty
        info("Verify row 1 input field is empty");
        softAssert.assertEquals(excPage.getRow1InputFieldText(), "", "Row 1 input field is not empty");
        
        info("Type text into input field");
        excPage.enterFoodInRow1("Sushi");
        info("Verify row 1 text changed");
        String text1 = excPage.getRow1InputFieldText();
        softAssert.assertEquals(text1,"Sushi", "Row 1 input field text is not Sushi");
        infoFormat("Test Case Name %s ", fullTestName);
        softAssert.assertAll();
    }
    @Test(dataProviderClass = TestDataProviders.class, 
          dataProvider = "dataProviderExceptionSuite")
    public void testStaleElementException(String testName) {
        String fullTestName = createFullTestName(testName);
        testReport = extent.createTest(fullTestName);
        info("Open page");
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); // Open page
        info("Verify the instructions text is visible");
        Assert.assertTrue(excPage.isInstructionsVisible(), "Instructions text is not displayed");
        info("Click Add button");
        excPage.clickAdd();
        info("Verify the instructions text is no longer visible");
        softAssert.assertTrue(excPage.isInstructionsInvisibleAfterWait(), "Instructions text is still displayed after edit.");
        infoFormat("Test Case Name %s ", fullTestName);
        softAssert.assertAll();
    }
    private String createFullTestName(String inputTestName) { 
        String endTestName = inputTestName != null && !inputTestName.equals("testName") ? inputTestName : "Login Test";
        return getTestNameAppend() + endTestName;
    }
}
