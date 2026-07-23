package com.practicetestautomation.tests.table;

import com.practicetestautomation.dataprovider.TestDataProviders;
import com.practicetestautomation.pageobjects.PracticeTablePage;
import com.practicetestautomation.tests.BaseTest;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for https://practicetestautomation.com/practice-test-table/
 *
 * TC1 – Language filter → Java
 *   Open page, select Language = Java, verify only Java courses are visible.
 *
 * TC2 – Level filter → Beginner only
 *   Open page, uncheck Intermediate and Advanced, verify only Beginner courses are visible.
 *
 * TC3 – Min enrollments → 10,000+
 *   Open page, open "Min enrollments" dropdown and choose 10,000+,
 *   verify every visible row shows enrollments >= 10,000.
 */
public class PracticeTableTest extends BaseTest {

    @Test(groups = {"smoke", "table"}, dataProviderClass = TestDataProviders.class, dataProvider = "dataProviderTableSuite")
    public void tc1_languageFilterJava(String testName) {
        info("Starting Test: " + testName);
        PracticeTablePage page = new PracticeTablePage(driver);
        info("Open page");
        page.visit();
        info("Select language: Java");
        page.selectLanguage("Java");
        info("Verify one course is visible");
        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one Java course to be visible");

        info("Verify all rows visible are Java");
        for (WebElement row : rows) {
            String lang = page.getCellText(row, "language");
            Assert.assertEquals(lang, "Java",
                "Expected language 'Java' but found '" + lang + "' for course: "
                + page.getCellText(row, "course"));
        }
        pass("TC1 PASSED - all " + rows.size() + " visible rows have Language = Java");
    }

    @Test(groups = {"smoke", "table"}, dataProviderClass = TestDataProviders.class, dataProvider = "dataProviderTableSuite")
    public void tc2_levelFilterBeginnerOnly(String testName) {
        info("Starting Test: " + testName != null ? testName : "Login Test");
        PracticeTablePage page = new PracticeTablePage(driver);
        info("Open page");
        page.visit();
        info("Uncheck Intermediate");
        page.uncheckLevel("Intermediate");
        info("Uncheck Advanced");
        page.uncheckLevel("Advanced");
        
        info("Verify one course is visible");
        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one Beginner course to be visible");

        info("Verify all rows visible are Beginner");
        for (WebElement row : rows) {
            String level = page.getCellText(row, "level");
            Assert.assertEquals(level, "Beginner",
                "Expected level 'Beginner' but found '" + level + "' for course: "
                + page.getCellText(row, "course"));
        }
        pass("TC2 PASSED - all " + rows.size() + " visible rows have Level = Beginner");
    }

    @Test(groups = {"smoke", "table"}, 
            dataProviderClass = TestDataProviders.class, 
            dataProvider = "dataProviderTableSuite")
    public void tc3_minEnrollments10000Plus(String testName) {
        info("Starting Test: " + testName != null ? testName : "Login Test");
        PracticeTablePage page = new PracticeTablePage(driver);
        info("Open page");
        page.visit();

        info("Select 10000+ in Min Enrollments");
        page.selectMinEnrollments("10,000+");
        
        info("Verify one course is visible");
        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one course with >= 10,000 enrollments");

        info("Verify all rows visible have enrollments > = 10000");
        for (WebElement row : rows) {
            int enrollments = page.getEnrollments(row);
            Assert.assertTrue(enrollments >= 10000,
                "Expected enrollments >= 10,000 but found " + enrollments
                + " for course: " + page.getCellText(row, "course"));
        }
        pass("TC3 PASSED - all " + rows.size() + " visible rows have enrollments >= 10,000");
    }
}
