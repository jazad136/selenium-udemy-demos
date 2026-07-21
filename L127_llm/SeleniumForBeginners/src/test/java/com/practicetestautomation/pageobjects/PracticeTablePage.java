package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page object for https://practicetestautomation.com/practice-test-table/.
 *
 * <p>The page contains a filterable / sortable table of automation courses.
 * Filter controls:
 * <ul>
 *   <li>Language – radio buttons (name="lang", values: Any | Java | Python)</li>
 *   <li>Level    – checkboxes  (name="level", values: Beginner | Intermediate | Advanced)</li>
 *   <li>Min enrollments – custom dropdown (#enrollDropdown, data-value: any | 5000 | 10000 | 50000)</li>
 *   <li>Sort by  – native select (#sortBy, values: col_id | col_course | col_lang | col_level | col_enroll)</li>
 * </ul>
 */
public class PracticeTablePage extends BasePage {

    private static final String URL = "https://practicetestautomation.com/practice-test-table/";

    // ── Landmark ──────────────────────────────────────────────────────────────
    private static final By TABLE = By.id("courses_table");

    // ── Filter controls ───────────────────────────────────────────────────────
    private static final By ENROLL_DROPDOWN        = By.id("enrollDropdown");
    private static final By ENROLL_DROPDOWN_BUTTON = By.cssSelector("#enrollDropdown .dropdown-button");
    private static final By RESET_BUTTON           = By.id("resetFilters");
    private static final By SORT_SELECT            = By.id("sortBy");
    private static final By NO_DATA                = By.id("noData");

    // ── Table rows ────────────────────────────────────────────────────────────
    private static final By TABLE_ROWS = By.cssSelector("#courses_table tbody tr");

    // ── Dynamic locators ──────────────────────────────────────────────────────
    private By langRadio(String value) {
        return By.cssSelector("input[name='lang'][value='" + value + "']");
    }

    private By levelCheckbox(String value) {
        return By.cssSelector("input[name='level'][value='" + value + "']");
    }

    private By enrollOption(String dataValue) {
        return By.cssSelector("#enrollDropdown li[data-value='" + dataValue + "']");
    }

    // ─────────────────────────────────────────────────────────────────────────

    public PracticeTablePage(WebDriver driver) {
        super(driver);
    }

    /** Navigate to the practice table page and wait for the table to be visible. */
    public void visit() {
        visit(URL);
        waitForElement(TABLE);
    }

    // ── Language filter ───────────────────────────────────────────────────────

    /** Select a language radio button (e.g. "Any", "Java", "Python"). */
    public void selectLanguage(String language) {
        waitForElement(langRadio(language)).click();
        waitForFilterToApply();
    }

    // ── Level filter ──────────────────────────────────────────────────────────

    /** Uncheck a level checkbox if it is currently checked. */
    public void uncheckLevel(String level) {
        WebElement cb = waitForElement(levelCheckbox(level));
        if (cb.isSelected()) {
            cb.click();
            waitForFilterToApply();
        }
    }

    /** Check a level checkbox if it is not currently checked. */
    public void checkLevel(String level) {
        WebElement cb = waitForElement(levelCheckbox(level));
        if (!cb.isSelected()) {
            cb.click();
            waitForFilterToApply();
        }
    }

    // ── Min-enrollments dropdown ──────────────────────────────────────────────

    /**
     * Select a min-enrollments option from the custom dropdown.
     * @param dataValue "any", "5000", "10000", or "50000"
     */
    public void selectMinEnrollments(String dataValue) {
        waitForElement(ENROLL_DROPDOWN_BUTTON).click();
        waitForElement(enrollOption(dataValue)).click();
        waitForFilterToApply();
    }

    // ── Sort ──────────────────────────────────────────────────────────────────

    /**
     * Change the sort column.
     * @param sortValue "col_id" | "col_course" | "col_lang" | "col_level" | "col_enroll"
     */
    public void sortBy(String sortValue) {
        WebElement select = waitForElement(SORT_SELECT);
        new Select(select).selectByValue(sortValue);
        waitForFilterToApply();
    }

    // ── Reset ─────────────────────────────────────────────────────────────────

    /** Click the Reset button. */
    public void clickReset() {
        waitForElement(RESET_BUTTON).click();
        waitForFilterToApply();
    }

    /** @return true if the Reset button is currently visible. */
    public boolean isResetButtonVisible() {
        return waitForIsDisplayed(RESET_BUTTON);
    }

    // ── Table queries ─────────────────────────────────────────────────────────

    /** @return all tbody rows (including hidden ones). */
    public List<WebElement> getAllRows() {
        return driver.findElements(TABLE_ROWS);
    }

    /**
     * @return tbody rows that are visible (not hidden by filters).
     *         Returns an empty list when the table is hidden by the no-data state.
     */
    public List<WebElement> getVisibleRows() {
        // The table element itself is hidden when all rows are filtered out.
        // Use isDisplayed() (no wait) to guard against a timeout in that state.
        if (!isDisplayed(TABLE)) {
            return java.util.Collections.emptyList();
        }
        return driver.findElements(TABLE_ROWS).stream()
                .filter(WebElement::isDisplayed)
                .collect(java.util.stream.Collectors.toList());
    }

    /** @return the text of a named cell within a row. */
    public String getCellText(WebElement row, String dataColValue) {
        return row.findElement(By.cssSelector("td[data-col='" + dataColValue + "']")).getText();
    }

    /** @return true if the "No matching courses." message is visible. */
    public boolean isNoDataMessageDisplayed() {
        return waitForIsDisplayed(NO_DATA);
    }

    /** @return true if the "No matching courses." message is hidden. */
    public boolean isNoDataMessageHidden() {
        return waitForIsHidden(NO_DATA);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void waitForFilterToApply() {
        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }
}
