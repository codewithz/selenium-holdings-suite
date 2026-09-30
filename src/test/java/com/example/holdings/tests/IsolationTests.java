package com.example.holdings.tests;

import com.example.holdings.api.ApiClient;
import com.example.holdings.api.ApiClient.SeededAccount;
import com.example.holdings.pages.DashboardPage;
import com.example.holdings.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsolationTests extends BaseTest {

    @Test
    @DisplayName("12 A freshly seeded account starts with the default holdings and balance")
    void seededAccountStartsClean() {
        SeededAccount account = ApiClient.seedAccount();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", account.username(), account.password());
        loginPage.waitForDashboard();

        DashboardPage dashboard = new DashboardPage(driver);
        assertEquals("Welcome, " + account.username(), dashboard.welcomeText());
        assertEquals(3, dashboard.holdingCount());
        assertEquals("₹1,25,000.50", dashboard.balanceText());
    }
}
