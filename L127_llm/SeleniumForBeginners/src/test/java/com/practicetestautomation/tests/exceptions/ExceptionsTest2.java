package com.practicetestautomation.tests.exceptions;

import com.practicetestautomation.pageobjects.ExceptionsPage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionsTest2 extends BaseTest {
    @Test
    public void testNoSuchElementException() { 
        // Open Page
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        // Click Add button
        exceptionsPage.clickAdd();
        // Wait for row 2 to load
        exceptionsPage.loadRow2();
        // Assert that row 2 is loaded
        Assert.assertTrue(exceptionsPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testTimeoutException() { 
        // Open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for 3 seconds for the second input field to be displayed
        excPage.loadRow2();
        Assert.assertTrue(excPage.isRow2FieldDisplayed(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testElementNotInteractibleException() { 
        // Open Page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for the second row to load
        excPage.loadRow2();
        // Type text into the second input field
        String inputText = "Hamburger";
        excPage.enterRow2Text(inputText);
        // Push Save button using locator By.name(“Save”)
        excPage.saveRowTwo();
        // Verify text saved
        String expectedText = "Row 2 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText,  "Message is not displayed.");
        // This page contains two elements with attribute name=”Save”.
        // The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }
    
    @Test
    public void testInvalidElementStateException() {
        
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); // Open page
        excPage.clickEdit(); // click Edit Button
        excPage.clearRow1InputField(); // Clear input field
        // The input field is disabled. Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.
        // If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.

        // addl test: verify text field is empty
        Assert.assertEquals(excPage.getRow1InputFieldText(), "", "Row 1 input field is not empty");
        // Type text into the input field
        excPage.enterFoodInRow1("Sushi");
        // Verify Text Changed
        String text1 = excPage.getRow1InputFieldText();
        Assert.assertEquals(text1,"Sushi", "Row 1 input field text is not Hamburger");
    }
    @Test
    public void testStaleElementException() {
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit(); // Open page
        Assert.assertTrue(excPage.isInstructionsVisible(), "Instructions text is not displayed");
        // Push add button
        excPage.clickAdd();
        // Verify instruction text element is no longer displayed
        Assert.assertTrue(excPage.isInstructionsInvisibleAfterWait(), "Instructions text is still displayed after edit.");
    }
}
