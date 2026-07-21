package com.practicetestautomation.tests.table;

import com.practicetestautomation.pageobjects.PracticeTablePage;
import com.practicetestautomation.tests.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Tests for the Practice Test Table page at
 * https://practicetestautomation.com/practice-test-table/
 */
public class PracticeTableTest extends BaseTest {

    // ── TC1: Language filter — Java ───────────────────────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc1_languageFilterJava() {
        info("TC1 – Language filter: select Java, verify only Java courses are visible");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectLanguage("Java");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "TC1: Expected at least one Java course row to be visible");
        for (WebElement row : rows) {
            String lang = page.getCellText(row, "language");
            Assert.assertEquals(lang, "Java",
                    "TC1: Expected language 'Java' but found '" + lang + "'");
        }
        pass("TC1 PASSED – all visible rows have Language = Java");
    }

    // ── TC2: Level filter — Beginner only ─────────────────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc2_levelFilterBeginnerOnly() {
        info("TC2 – Level filter: uncheck Intermediate and Advanced, verify only Beginner visible");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.uncheckLevel("Intermediate");
        page.uncheckLevel("Advanced");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "TC2: Expected at least one Beginner course row");
        for (WebElement row : rows) {
            String level = page.getCellText(row, "level");
            Assert.assertEquals(level, "Beginner",
                    "TC2: Expected level 'Beginner' but found '" + level + "'");
        }
        pass("TC2 PASSED – all visible rows have Level = Beginner");
    }

    // ── TC3: Min enrollments — 10,000+ ────────────────────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc3_minEnrollments10k() {
        info("TC3 – Min enrollments filter: select 10,000+, verify every row >= 10000");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectMinEnrollments("10000");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "TC3: Expected at least one row with enrollments >= 10,000");
        for (WebElement row : rows) {
            String enrollText = page.getCellText(row, "enrollments");
            int enrollments = Integer.parseInt(enrollText.replace(",", ""));
            Assert.assertTrue(enrollments >= 10000,
                    "TC3: Expected enrollments >= 10000 but found " + enrollments);
        }
        pass("TC3 PASSED – all visible rows have enrollments >= 10,000");
    }

    // ── TC4: Combined filters — Python + Beginner + 10,000+ ──────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc4_combinedFiltersPythonBeginner10k() {
        info("TC4 – Combined: Python + Beginner + 10,000+");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectLanguage("Python");
        page.uncheckLevel("Intermediate");
        page.uncheckLevel("Advanced");
        page.selectMinEnrollments("10000");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertFalse(rows.isEmpty(), "TC4: Expected at least one matching Python Beginner >= 10,000 row");
        for (WebElement row : rows) {
            String lang    = page.getCellText(row, "language");
            String level   = page.getCellText(row, "level");
            String enrollText = page.getCellText(row, "enrollments");
            int enroll     = Integer.parseInt(enrollText.replace(",", ""));

            Assert.assertEquals(lang,  "Python",   "TC4: Expected language Python, found " + lang);
            Assert.assertEquals(level, "Beginner", "TC4: Expected level Beginner, found " + level);
            Assert.assertTrue(enroll >= 10000,      "TC4: Expected enrollments >= 10000, found " + enroll);
        }
        pass("TC4 PASSED – combined filters return only Python Beginner courses with >= 10,000 enrollments");
    }

    // ── TC5: No results state ────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression", "negative", "table"})
    public void tc5_noResultsState() {
        info("TC5 – No results: select Python + Advanced (no such combination), verify no-data message");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectLanguage("Python");
        page.uncheckLevel("Beginner");
        page.uncheckLevel("Intermediate");

        Assert.assertTrue(page.isNoDataMessageDisplayed(),
                "TC5: Expected 'No matching courses.' to be displayed when no rows match filters");
        List<WebElement> rows = page.getVisibleRows();
        Assert.assertTrue(rows.isEmpty(),
                "TC5: Expected zero visible rows when no courses match the selected filters");
        pass("TC5 PASSED – 'No matching courses.' message is shown and no rows are visible");
    }

    // ── TC6: Reset button visibility and behaviour ────────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc6_resetButtonBehavior() {
        info("TC6 – Reset: change a filter, verify Reset visible, click Reset, verify defaults restored");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.selectLanguage("Java");
        Assert.assertTrue(page.isResetButtonVisible(),
                "TC6: Reset button should be visible after changing Language filter");

        page.clickReset();

        // After reset all 9 rows must be visible
        List<WebElement> rows = page.getVisibleRows();
        Assert.assertEquals(rows.size(), 9,
                "TC6: Expected all 9 rows visible after Reset, found " + rows.size());

        // Verify Language radio = Any (page should show all languages)
        long javaCount   = rows.stream().filter(r -> page.getCellText(r, "language").equals("Java")).count();
        long pythonCount = rows.stream().filter(r -> page.getCellText(r, "language").equals("Python")).count();
        Assert.assertTrue(javaCount > 0 && pythonCount > 0,
                "TC6: After Reset both Java and Python courses should be visible (mixed languages)");
        pass("TC6 PASSED – Reset restores all 9 rows; mixed languages visible");
    }

    // ── TC7: Sort by Enrollments (numeric ascending) ──────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc7_sortByEnrollmentsAscending() {
        info("TC7 – Sort by Enrollments, verify ascending numeric order");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.sortBy("col_enroll");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertTrue(rows.size() > 1, "TC7: Expected more than one row to verify sort order");

        int previous = -1;
        for (WebElement row : rows) {
            String enrollText = page.getCellText(row, "enrollments");
            int current = Integer.parseInt(enrollText.replace(",", ""));
            Assert.assertTrue(current >= previous,
                    "TC7: Rows not in ascending enrollment order — found " + current + " after " + previous);
            previous = current;
        }
        pass("TC7 PASSED – rows are in ascending numeric enrollment order");
    }

    // ── TC8: Sort by Course Name (alphabetical) ───────────────────────────────

    @Test(groups = {"smoke", "regression", "table"})
    public void tc8_sortByCourseNameAlphabetical() {
        info("TC8 – Sort by Course Name, verify A→Z alphabetical order");
        PracticeTablePage page = new PracticeTablePage(driver);
        page.visit();

        page.sortBy("col_course");

        List<WebElement> rows = page.getVisibleRows();
        Assert.assertTrue(rows.size() > 1, "TC8: Expected more than one row to verify sort order");

        String previous = "";
        for (WebElement row : rows) {
            String current = page.getCellText(row, "course");
            Assert.assertTrue(current.compareToIgnoreCase(previous) >= 0,
                    "TC8: Rows not in alphabetical order — found '" + current + "' after '" + previous + "'");
            previous = current;
        }
        pass("TC8 PASSED – rows are in alphabetical (A→Z) order by course name");
    }
}
