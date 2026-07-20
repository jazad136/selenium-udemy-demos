package com.practicetestautomation.tests.table;

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

    // ── TC1 ────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "table"})
    public void tc1_languageFilterJava() {
        info("TC1 – Language filter: Java");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectLanguage("Java");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one Java course to be visible");

        for (WebElement row : rows) {
            String lang = page.getCellText(row, "language");
            Assert.assertEquals(lang, "Java",
                "Expected language 'Java' but found '" + lang + "' for course: "
                + page.getCellText(row, "course"));
        }
        pass("TC1 PASSED – all " + rows.size() + " visible rows have Language = Java");
    }

    // ── TC2 ────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "table"})
    public void tc2_levelFilterBeginnerOnly() {
        info("TC2 – Level filter: Beginner only");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.uncheckLevel("Intermediate");
        page.uncheckLevel("Advanced");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one Beginner course to be visible");

        for (WebElement row : rows) {
            String level = page.getCellText(row, "level");
            Assert.assertEquals(level, "Beginner",
                "Expected level 'Beginner' but found '" + level + "' for course: "
                + page.getCellText(row, "course"));
        }
        pass("TC2 PASSED – all " + rows.size() + " visible rows have Level = Beginner");
    }

    // ── TC3 ────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "table"})
    public void tc3_minEnrollments10000Plus() {
        info("TC3 – Min enrollments: 10,000+");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectMinEnrollments("10,000+");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "Expected at least one course with >= 10,000 enrollments");

        for (WebElement row : rows) {
            int enrollments = page.getEnrollments(row);
            Assert.assertTrue(enrollments >= 10000,
                "Expected enrollments >= 10,000 but found " + enrollments
                + " for course: " + page.getCellText(row, "course"));
        }
        pass("TC3 PASSED – all " + rows.size() + " visible rows have enrollments >= 10,000");
    }
}
