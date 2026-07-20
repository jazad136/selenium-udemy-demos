---
name: selenium-new-test
description: Use when the user wants to create, add, or scaffold a new Selenium/TestNG test in this project. Guides through creating the test class, registering TestNG groups, and updating the relevant test suite XML files.
---

# Create a New Selenium Test

Follow these steps every time a new test is added to this project.

---

## Project Structure

```
src/test/java/com/practicetestautomation/
  pageobjects/          ← one class per page, extends BasePage
  tests/<feature>/      ← test class, extends BaseTest
  testsetup/            ← framework infrastructure only (do not modify)
src/test/resources/
  TestSuites/           ← TestNG XML suite files
  ExtentConfigs/        ← Extent Reports config (do not modify)
```

---

## Test Class Rules (`tests/<feature>/`)

- **Extends `BaseTest`** — never re-declare `WebDriver driver`, `Logger logger`, `ExtentTest testReport`, `setUp()`, or `tearDown()`.
- Do **not** re-import `ChromeDriver`, `FirefoxDriver`, `@BeforeMethod`, `@AfterMethod`, `@Parameters`, or `ExtentReportManager` in a test class.
- Use inherited helpers `info(msg)`, `pass(msg)`, `fail(msg)`, `skip(msg)` for logging — do not call `System.out.println` directly.
- Each test method is annotated `@Test(groups = {"<group1>", ...})`.
- Use descriptive, camelCase method names prefixed with `test` or a `tc<N>_` prefix for numbered cases.
- Parameterised tests use `@Parameters({"param1", ...})` and receive values from the TestNG suite XML.
- Assertions use `org.testng.Assert` only.
- Each test follows the Arrange → Act → Assert pattern:
  1. Construct the page object: `MyPage page = new MyPage(driver);`
  2. Call `page.visit();`
  3. Interact via page-object methods.
  4. Assert with a descriptive failure message as the third argument.
  5. End with `pass("TC... PASSED – <detail>");`

---

## TestNG Group Names (canonical)

| Group | Meaning |
|---|---|
| `smoke` | Fast sanity, run on every commit |
| `regression` | Full regression |
| `positive` | Happy-path tests |
| `negative` | Error / boundary tests |
| `table` | Table/filter feature tests |
| `exceptions` | Selenium-exception demonstration tests |

---

## Step 1 — Gather Information

Before writing code, ask the user (use `ask_followup_question` if any of these are unknown):
- What page / feature is the test for?
- Does a page object for this page already exist?
- What are the test cases (actions + assertions)?
- Which TestNG groups should the test belong to?
- Which existing suite XML file(s) should include the new test?

---

## Step 2 — Create or Update the Page Object

If a page object is needed, activate the `selenium-page-object` skill and follow its rules to
create `src/test/java/com/practicetestautomation/pageobjects/<FeatureName>Page.java`.

---

## Step 3 — Create the Test Class

1. Create `src/test/java/com/practicetestautomation/tests/<feature>/<FeatureName>Test.java`.
2. Follow the Test Class rules above.
3. Example skeleton:

```java
package com.practicetestautomation.tests.<feature>;

import com.practicetestautomation.pageobjects.FeatureNamePage;
import com.practicetestautomation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FeatureNameTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void testSomeFunctionality() {
        info("TC1 – describe the test");
        FeatureNamePage page = new FeatureNamePage(driver);
        page.visit();

        page.doSomething();

        Assert.assertTrue(page.isSomethingDisplayed(), "Expected element to be visible after action");
        pass("TC1 PASSED – element is visible");
    }
}
```

---

## Step 4 — Update the TestNG Suite XML

For each suite that should run the new test:
1. Open the relevant file under `src/test/resources/TestSuites/`.
2. Add a `<test>` block (with a `<parameter name="browser" value="chrome"/>` if multi-browser is needed).
3. Use `<groups><run><include name="<group>"/></run></groups>` to run by group, **or** `<methods>/<include name="<method>"/>` to pin specific methods.
4. Suite XML skeleton for a new feature suite:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">

<suite name="Feature Name Test Suite" verbose="1">
    <test name="Feature Tests - Chrome">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="com.practicetestautomation.tests.feature.FeatureNameTest"/>
        </classes>
    </test>
</suite>
```

If the test should also appear in `FullRegressionSuite.xml`, add a `<test>` block there as well.

---

## Step 5 — Verify

After writing all files, remind the user to run the suite with Maven and confirm the new test
appears in the Extent Report and passes.
