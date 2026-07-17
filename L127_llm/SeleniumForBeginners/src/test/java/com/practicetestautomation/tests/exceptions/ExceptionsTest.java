package com.practicetestautomation.tests.exceptions;

import com.practicetestautomation.pageobjects.ExceptionsPage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionsTest extends BaseTest {

    @Test
    public void testNoSuchElementException() { 
        // Open Page
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        // Click Add button
        exceptionsPage.clickAdd();
        // Wait for row 2 to load
        // Assert that row 2 is loaded
        Assert.assertTrue(exceptionsPage.isRowTwoDisplayedAfterWait(), "Row 2 input field is not displayed.");
    }
    
    @Test
    public void testTimeoutException() { 
        // open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
//         Wait for 3 seconds for the second input field to be displayed
//        excPage.loadRow2In3Seconds();
        Assert.assertTrue(excPage.isRowTwoDisplayedAfterWait(), "Row 2 input field was not displayed in 3 seconds.");
    }
    
    @Test
    public void testElementNotInteractibleException() { 
        // Open Page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Click Add button
        excPage.clickAdd();
        // Wait for the second row to load
        excPage.isRowTwoDisplayedAfterWait();
        // Type text into the second input field
        String inputText = "Hamburger";
        excPage.enterFoodInRow2(inputText);
        // Push Save button using locator By.name("Save")
        excPage.saveRowTwo();
        // Verify text saved
        String expectedText = "Row 2 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText, "Message is not displayed.");
        // This page contains two elements with attribute name=”Save”.
        // The first one is invisible. So when we are trying to click on the invisible element, we get ElementNotInteractableException.

        // The same action used to throw ElementNotVisibleException, but now it throws a different exception (not sure if it’s a bug in Selenium or a feature)
    }
    
    @Test
    public void testInvalidElementStateException() {
        // Open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // click Edit Button
        excPage.clickEdit();
        // The input field is disabled. Trying to clear the disabled field will throw InvalidElementStateException. We need to enable editing of the input field first by clicking the Edit button.
        // If we try to type text into the disabled input field, we will get ElementNotInteractableException, as in Test case 2.
        // Type text into the input field
        
        // Clear input field
        excPage.enterFoodInRowOne("Sushi");
        // Click Save Button? 
        excPage.saveRowOne();
        // Verify text was saved?
        String expectedText = "Row 1 was saved";
        Assert.assertEquals(excPage.getSuccessMessage(), expectedText, "Row 1 was not saved. Label did not appear.");
    }
    @Test
    public void testStaleElementException() {
        // Open page
        ExceptionsPage excPage = new ExceptionsPage(driver);
        excPage.visit();
        // Push add button
        excPage.clickAdd();
        // Verify instruction text element is no longer displayed
        Assert.assertTrue(excPage.isInstructionsInvisibleAfterWait(), "Instructions text is still displayed");
    }
}
