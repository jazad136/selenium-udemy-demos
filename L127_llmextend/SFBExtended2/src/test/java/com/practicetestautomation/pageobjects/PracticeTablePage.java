package com.practicetestautomation.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object for https://practicetestautomation.com/practice-test-table/
 *
 * Table id="courses_table"  columns: ID | Course Name | Language | Level | Enrollments | Link
 * Filters: Language radio buttons (name="lang"), Level checkboxes (name="level"),
 *          Min enrollments custom dropdown (#enrollDropdown), Reset button (#resetFilters)
 */
public class PracticeTablePage extends BasePage {

    private static final String URL = "https://practicetestautomation.com/practice-test-table/";

    // Table
    private static final By TABLE        = By.id("courses_table");
    private static final By VISIBLE_ROWS = By.cssSelector("#courses_table tbody tr[style!='display: none;']");
    private static final By ALL_ROWS     = By.cssSelector("#courses_table tbody tr");

    // Language radio buttons  (value = "Any" | "Java" | "Python")
    private By langRadio(String lang) {
        return By.cssSelector("input[name='lang'][value='" + lang + "']");
    }

    // Level checkboxes  (value = "Beginner" | "Intermediate" | "Advanced")
    private By levelCheckbox(String level) {
        return By.cssSelector("input[name='level'][value='" + level + "']");
    }

    // Min enrollments dropdown
    private static final By ENROLL_DROPDOWN_BTN  = By.cssSelector("#enrollDropdown .dropdown-button");
    // option items inside the dropdown menu
    private By enrollOption(String text) {
        return By.xpath("//div[@id='enrollDropdown']//li[normalize-space(.)='" + text + "']");
    }

    // Reset button
    private static final By RESET_BTN = By.id("resetFilters");

    public PracticeTablePage(WebDriver driver) {
        super(driver);
    }

    public void visit() {
        visit(URL);
        waitForElement(TABLE);
    }

    // ── Language filter ────────────────────────────────────────────────────────

    /** Clicks the radio button for the given language (e.g. "Java"). */
    public void selectLanguage(String lang) {
        WebElement radio = waitForElement(langRadio(lang));
        if (!radio.isSelected()) radio.click();
        waitForFilterToApply();
    }

    // ── Level filter ───────────────────────────────────────────────────────────

    /** Unchecks a level checkbox if it is currently checked. */
    public void uncheckLevel(String level) {
        WebElement cb = waitForElement(levelCheckbox(level));
        if (cb.isSelected()) cb.click();
        waitForFilterToApply();
    }

    /** Checks a level checkbox if it is not already checked. */
    public void checkLevel(String level) {
        WebElement cb = waitForElement(levelCheckbox(level));
        if (!cb.isSelected()) cb.click();
        waitForFilterToApply();
    }

    // ── Min enrollments filter ─────────────────────────────────────────────────

    /** Opens the dropdown and selects the given option label (e.g. "10,000+"). */
    public void selectMinEnrollments(String label) {
        waitForElement(ENROLL_DROPDOWN_BTN).click();
        waitForElement(enrollOption(label)).click();
        waitForFilterToApply();
    }

    // ── Table data ─────────────────────────────────────────────────────────────

    /**
     * Returns all rows that are currently VISIBLE (not hidden by CSS).
     * The JS filter sets display:none on hidden rows; we match rows where the
     * computed style is not 'none'. We use JS to check visibility.
     */
    public List<WebElement> getVisibleRows() {
        waitForElement(TABLE);
        List<WebElement> all = driver.findElements(ALL_ROWS);
        return all.stream()
                  .filter(r -> !r.getCssValue("display").equals("none"))
                  .toList();
    }

    /** Returns the text of the given data-col cell in the row. */
    public String getCellText(WebElement row, String colName) {
        return row.findElement(By.cssSelector("td[data-col='" + colName + "']"))
                  .getText().trim();
    }

    /** Returns the enrollment count of a row as an integer. */
    public int getEnrollments(WebElement row) {
        String raw = getCellText(row, "enrollments").replace(",", "").replace(" ", "");
        return Integer.parseInt(raw);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    /** Small pause to let the JS filter re-render the table rows. */
    private void waitForFilterToApply() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }
}
