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

## Tested URLs
| Page | URL |
|---|---|
| Login | https://practicetestautomation.com/practice-test-login/ |
| Exceptions | https://practicetestautomation.com/practice-test-exceptions/ |
