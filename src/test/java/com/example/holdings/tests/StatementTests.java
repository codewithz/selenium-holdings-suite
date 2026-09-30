package com.example.holdings.tests;

import com.example.holdings.api.ApiClient;
import com.example.holdings.api.ApiClient.SeededAccount;
import com.example.holdings.pages.DashboardPage;
import com.example.holdings.pages.LoginPage;
import com.example.holdings.pages.StatementPage;
import com.example.holdings.util.WaitUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatementTests extends BaseTest {

    private String mainWindow;

    @BeforeEach
    void logInWithFreshAccount() {
        SeededAccount account = ApiClient.seedAccount();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", account.username(), account.password());
        loginPage.waitForDashboard();
        mainWindow = driver.getWindowHandle();
    }

    @AfterEach
    void closeExtraWindows() {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(mainWindow);
    }

    private StatementPage openStatementWindow() {
        new DashboardPage(driver).openStatementLink();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
            }
        }
        return new StatementPage(driver);
    }

    @Test
    @DisplayName("13 The statement opens in a new window")
    void statementOpensInNewWindow() {
        new DashboardPage(driver).openStatementLink();
        WaitUtils.sleep(2000);
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        assertEquals(2, handles.size());

        driver.switchTo().window(handles.get(1));
        StatementPage statement = new StatementPage(driver);
        assertEquals("Account Statement", statement.headingText());

        driver.close();
        driver.switchTo().window(mainWindow);
        assertTrue(new DashboardPage(driver).welcomeText().startsWith("Welcome, qa_"));
    }

    @Test
    @DisplayName("14 The terms can be accepted inside the iframe")
    void termsCanBeAcceptedInIframe() {
        StatementPage statement = openStatementWindow();

        assertEquals("Terms accepted", statement.acceptTermsInFrame());
        assertEquals("Account Statement", statement.headingText());
    }

    @Test
    @DisplayName("15 The market filter accepts several selections")
    void marketFilterMultiSelect() {
        StatementPage statement = openStatementWindow();
        assertEquals(3, statement.rowCount());

        statement.marketFilter().selectByValue("BSE");
        assertEquals(1, statement.rowCount());
        assertTrue(statement.rowsText().contains("National Infra Bond"));

        statement.marketFilter().selectByValue("NSE");
        assertEquals(3, statement.rowCount());
        assertEquals(2, statement.marketFilter().getAllSelectedOptions().size());

        statement.marketFilter().deselectAll();
        assertEquals(3, statement.rowCount());
    }
}
