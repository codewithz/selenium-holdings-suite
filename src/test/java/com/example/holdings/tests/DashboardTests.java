package com.example.holdings.tests;

import com.example.holdings.pages.DashboardPage;
import com.example.holdings.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DashboardTests extends BaseTest {

    @BeforeEach
    void logIn() {
        loginAsDemo();
    }

    @Test
    @DisplayName("04 Dashboard lists the holdings for the account")
    void dashboardListsHoldings() {
        DashboardPage dashboard = new DashboardPage(driver);

        assertEquals(3, dashboard.holdingCount());
        assertEquals("Bluechip Growth Fund", dashboard.firstHoldingName());
        assertTrue(dashboard.firstHoldingMeta().startsWith("NSE"));
    }

    @Test
    @DisplayName("05 An account with no holdings shows the empty message")
    void emptyAccountShowsMessage() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logOut();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", "emptyholder", "demo1234");
        loginPage.waitForDashboard();

        assertEquals("No holdings remaining.", dashboard.noHoldingsText());
        assertEquals(0, dashboard.holdingCount());
    }
}
