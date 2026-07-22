# SeleniumForBeginners — Project Context

## What This Is
A Java/Maven Selenium WebDriver learning project (Demo 8 in a series). It tests two pages on the public practice site **https://practicetestautomation.com** using the Page Object Model pattern. The project is used to demonstrate common Selenium exception handling, login flows, and TestNG suite configuration.

## Tech Stack
| Concern | Technology |
|---|---|
| Language | Java 17 |
| Build | Maven (pom.xml) |
| Test runner | TestNG 7.12.0 |
| Browser automation | Selenium Java 4.45.0 |
| Reporting | ExtentReports 5.1.1 (Spark) |
| Browsers | Chrome, Firefox (WebDriverManager-free; drivers must be on PATH) |
| MCP server | `chrome-devtools-mcp@latest` via `.bob/mcp.json` (workspace-scoped) |

## Project Structure
```
src/
  main/java/com/jschway/example/firstselenium/
    SeleniumDemo.java          # Standalone scratch demo (not part of test suite)
  main/resources/
    msedgedriver.exe           # Bundled Edge driver (unused in active tests)

  test/java/com/practicetestautomation/
    pageobjects/
      BasePage.java            # Base POM: driver + WebDriverWait(10s), shared wait helpers
      LoginPage.java           # Login page POM → https://practicetestautomation.com/practice-test-login/
      SuccessfulLoginPage.java # Post-login page POM, waits for "Log out" link
      ExceptionsPage.java      # Exceptions demo page POM → https://practicetestautomation.com/practice-test-exceptions/
    tests/
      BaseTest.java            # @BeforeMethod: spins up Chrome/Firefox; @AfterMethod: driver.quit(); wires ExtentReports
      login/
        LoginTest.java         # Positive + negative login test cases
      exceptions/
        ExceptionsTest.java    # Exception scenario tests (refactored, uses BasePage wait helpers)
        ExceptionsTest2.java   # Older parallel version of exception tests (explicit loadRow2 calls)
    testsetup/
      ExtentReportManager.java # Singleton; loads extentConfig.xml, writes xml-config-report.html

  test/resources/
    ExtentConfigs/
      extentConfig.xml / .json # Spark reporter configuration
    TestSuites/
      FullRegressionSuite.xml  # Default suite (all login + exceptions)
      NegativeTestSuite.xml    # Negative login only (Chrome + Firefox)
      SmokeTestSuite.xml       # Smoke 
      ExceptionsTestSuite.xml
      DebugSuite.xml
```

## Key Design Decisions
- **Page Object Model**: every tested page has a dedicated POM class extending `BasePage`.
- **BasePage** centralises `WebDriverWait` (10-second default) and exposes helpers: `waitForElement`, `waitForIsDisplayed`, `waitForIsInvisible`, `waitForIsHidden`, `isDisplayed`.
- **BaseTest** drives browser lifecycle and ExtentReports. Browser is injected via TestNG `@Parameters("browser")`; defaults to `chrome` if not supplied.
- **ExtentReportManager** is a singleton — report output lands at `xml-config-report.html` in the project root.
- **Suite selection** at build time: `mvn test -DsuiteXmlFile=NegativeTestSuite` (default is `FullRegressionSuite`).

## Test Credentials (hardcoded, public practice site)
| Field | Value |
|---|---|
| Valid username | `student` |
| Valid password | `Password123` |
| Invalid username error | `Your username is invalid!` |
| Invalid password error | `Your password is invalid!` |

## How to Run
```bash
# Full regression (default)
mvn test

# Specific suite
mvn test -DsuiteXmlFile=NegativeTestSuite

# Available suite names (no .xml extension needed):
#   FullRegressionSuite, NegativeTestSuite, SmokeTestSuite,
#   ExceptionsTestSuite, DebugSuite
```

## Known Issues / Watch-outs
- **`ExceptionsTest3` does not exist** — `FullRegressionSuite.xml` and `SmokeTestSuite.xml` both reference `com.practicetestautomation.tests.exceptions.ExceptionsTest3`, which has not been created yet. Those suite runs will fail at class-load time.
- `ExceptionsTest` and `ExceptionsTest2` cover the same scenarios; `ExceptionsTest2` is the older more verbose version kept for co  mparison.
- `BaseTest.setUp` always names the ExtentReports test `"Login Test"` regardless of which test class runs — this is a known cosmetic bug.
- No WebDriverManager dependency; Chrome and Firefox binaries + drivers must be available on the system `PATH`.
- `doesnotcompile/TestSeleniumDemo.java` is intentionally excluded from the build (lives outside `src/`).

## Coding Rules

Two Bob skills enforce the project's coding conventions. Always activate the relevant skill before
writing or modifying code in the paths below.

### Page Objects — `src/test/java/com/practicetestautomation/pageobjects/`
Skill: `.bob/skills/selenium-page-object/SKILL.md`

Key rules:
- Every page object class extends `BasePage` and is named `<Feature>Page`.
- Constructor signature: `public FeaturePage(WebDriver driver) { super(driver); }`
- Fixed URL stored as `private static final String URL`; never hard-coded inside `visit()`.
- `public void visit()` calls `super.visit(URL)` then waits for a landmark element.
- Locators: `private static final By NAME` (fixed) or a `private By name(…)` method (dynamic).
- Use `BasePage` wait helpers (`waitForElement`, `waitForIsDisplayed`, `waitForIsInvisible`) — no raw `WebDriverWait` unless a non-standard timeout is needed.
- No assertions (`Assert.*`) in page objects.
- No `WebElement` fields or raw `driver.findElement` calls exposed to test classes.
- `Thread.sleep` only inside `private waitForXxxToApply()` helpers.

### Test Classes — `src/test/java/com/practicetestautomation/tests/<feature>/`
Skill: `.bob/skills/selenium-new-test/SKILL.md`

Key rules:
- Every test class extends `BaseTest`; never re-declare `driver`, `logger`, `setUp()`, or `tearDown()`.
- Do not import `ChromeDriver`, `FirefoxDriver`, `@BeforeMethod`, `@AfterMethod`, or `ExtentReportManager`.
- Log with inherited helpers: `info(msg)`, `pass(msg)`, `fail(msg)`, `skip(msg)`.
- Annotate each test method `@Test(groups = {"smoke", "regression", …})`.
- Assertions use `org.testng.Assert` only; always supply a descriptive failure message.
- Follow Arrange → Act → Assert; end every passing test with `pass("TCN PASSED – <detail>")`.

### Suite XML — `src/test/resources/TestSuites/`
- Add a `<test>` block (with `<parameter name="browser" value="chrome"/>`) for each new test class.
- Run by group via `<groups><run><include name="…"/></run></groups>` or pin methods with `<methods><include name="…"/>`.
- Register new tests in `FullRegressionSuite.xml` in addition to any feature-specific suite.

---

## Tested URLs
| Page | URL |
|---|---|
| Login | https://practicetestautomation.com/practice-test-login/ |
| Exceptions | https://practicetestautomation.com/practice-test-exceptions/ |
