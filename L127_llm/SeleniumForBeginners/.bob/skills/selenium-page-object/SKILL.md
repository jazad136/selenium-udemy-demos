---
name: selenium-page-object
description: Use when the user wants to create, review, or refactor a page object in this project. Enforces the naming, structure, locator, and API conventions derived from the existing page objects (BasePage, LoginPage, ExceptionsPage, PracticeTablePage, SuccessfulLoginPage).
---

# Page Object Rules — SeleniumForBeginners

Apply every rule below whenever creating or modifying a page object in
`src/test/java/com/practicetestautomation/pageobjects/`.

---

## 1. Class Declaration

- Class name ends with `Page` and matches the filename: `FeaturePage.java` → `public class FeaturePage`.
- Always `extends BasePage` — never hold a raw `WebDriver` field.
- Package: `package com.practicetestautomation.pageobjects;`

```java
public class FeaturePage extends BasePage {
    ...
}
```

---

## 2. Constructor

- Single constructor, `public`, accepts only `WebDriver driver`.
- First statement is `super(driver);` — nothing else required unless a non-default `WebDriverWait`
  is needed (as in `ExceptionsPage`).

```java
public FeaturePage(WebDriver driver) {
    super(driver);
}
```

---

## 3. URL Constant

- If the page has a fixed URL, declare it as:
  `private static final String URL = "https://...";`
- Do **not** hard-code the URL string inside `visit()`.

---

## 4. Locators

| Scenario | Declaration style |
|---|---|
| Fixed, compile-time locator | `private static final By SOME_ELEMENT = By.id("some-id");` |
| Dynamic (parameterised CSS / XPath) | `private By someElement(String param) { return By.cssSelector("...'" + param + "'..."); }` |

Rules:
- **All locators are `private`** — never `public` or `protected`.
- Fixed locators are `static final` and use SCREAMING_SNAKE_CASE.
- Dynamic locator helpers are `private` methods that return `By`, named in camelCase.
- Prefer `By.id` > `By.cssSelector` > `By.xpath` in that order of preference.
- For XPath, use `normalize-space(.)` when matching text that may have surrounding whitespace.

---

## 5. `visit()` Method

- `public void visit()` — no parameters.
- Calls `super.visit(URL)`.
- Immediately after navigation, wait for a landmark element that confirms the page is loaded:
  `waitForElement(LANDMARK_ELEMENT);`

```java
public void visit() {
    visit(URL);
    waitForElement(LANDMARK_ELEMENT);
}
```

---

## 6. Interactions and Queries

- **One public method per user action or observable state** — no logic leaks into test classes.
- Action methods (clicks, inputs) have `void` return type.
- Query methods (state checks) return `boolean`, `String`, `int`, `List<WebElement>`, etc.
- Use `BasePage` helpers — never create a raw `WebDriverWait` in a page object unless a
  non-standard timeout is genuinely required:

| Task | Use |
|---|---|
| Wait for element and get it | `waitForElement(locator)` |
| Boolean: is element visible after wait? | `waitForIsDisplayed(locator)` or `waitForIsDisplayed(locator, seconds)` |
| Boolean: is element hidden after wait? | `waitForIsInvisible(locator)` or `waitForIsHidden(locator)` |
| Boolean: is element present right now (no wait)? | `isDisplayed(locator)` *(protected in BasePage)* |
| Click an element | `waitForElement(locator).click()` |
| Type into a field | `driver.findElement(locator).sendKeys(text)` |
| Clear then type (replace text) | `WebElement el = waitForElement(locator); el.clear(); el.sendKeys(text);` |

- **Do not expose `WebElement` objects to test classes** unless the test must iterate over a
  collection of rows (as in `PracticeTablePage.getVisibleRows()`).
- For multi-row table pages, provide `List<WebElement> getVisibleRows()` and
  `String getCellText(WebElement row, String colName)` helpers so tests never call
  `driver.findElement` directly.

---

## 7. Waiting for Async UI Changes

- After triggering an async filter, sort, or AJAX update, add a private `waitForXxxToApply()`
  helper. Acceptable to use `Thread.sleep` **only** inside such private helpers.
- Do **not** use `Thread.sleep` in public methods.

```java
private void waitForFilterToApply() {
    try { Thread.sleep(500); } catch (InterruptedException ignored) {}
}
```

---

## 8. Imports

Import only what is used. Standard imports for a page object:

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;          // only if needed
import java.util.List;                           // only if needed
import org.openqa.selenium.support.ui.ExpectedConditions; // only if custom wait
import org.openqa.selenium.support.ui.WebDriverWait;      // only if custom wait
import java.time.Duration;                       // only with custom WebDriverWait
```

Never import `ChromeDriver`, `FirefoxDriver`, test annotations (`@Test`, `@BeforeMethod`), or
`ExtentReports` in a page object.

---

## 9. Javadoc

- Add a class-level Javadoc comment on any page with non-obvious page structure (columns, filters,
  dynamic IDs). See `PracticeTablePage` as a model.
- Add single-line Javadoc (`/** … */`) on public methods whose purpose is not self-evident from the
  method name.

---

## 10. What NOT to Do

- Do **not** place assertions (`Assert.*`) in page objects.
- Do **not** call `super.getCurrentUrl()` or `super.getPageSource()` redundantly if `BasePage`
  already provides them (see `SuccessfulLoginPage` — the overrides there add no value and should be
  avoided in new code).
- Do **not** duplicate methods with slightly different names for the same action (e.g.
  `enterFoodInRow1` and `enterRow1Text` — pick one clear name).
- Do **not** add `public` to locator fields.
- Do **not** create a separate `WebDriverWait` field unless the page genuinely requires a timeout
  that differs from the 10-second default in `BasePage`.
