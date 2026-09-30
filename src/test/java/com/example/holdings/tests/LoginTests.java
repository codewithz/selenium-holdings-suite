package com.example.holdings.tests;

import com.example.holdings.config.Config;
import com.example.holdings.pages.DashboardPage;
import com.example.holdings.pages.LoginPage;
import com.example.holdings.util.WaitUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTests extends BaseTest {

    @Test
    @DisplayName("01 Valid login lands on the dashboard")
    void validLoginLandsOnDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", "demo", "demo1234");
        loginPage.waitForDashboard();

        DashboardPage dashboard = new DashboardPage(driver);
        assertTrue(driver.getCurrentUrl().contains("/dashboard"));
        assertEquals("Welcome, demo", dashboard.welcomeText());
    }

    @Test
    @DisplayName("02 Wrong password shows an error")
    void wrongPasswordShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", "demo", "not-the-password");

        assertEquals("Invalid area, username or password", loginPage.errorText());
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @ParameterizedTest(name = "03 Every business area can log in: {0}")
    @CsvSource({
            "Retail Banking,demo",
            "Corporate Banking,demo2",
            "NRI Services,demo3"
    })
    void everyBusinessAreaCanLogIn(String area, String username) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(area, username, "demo1234");
        loginPage.waitForDashboard();

        DashboardPage dashboard = new DashboardPage(driver);
        assertEquals("Welcome, " + username, dashboard.welcomeText());
        assertTrue(dashboard.areaText().equals(area));
    }

    @Test
    @DisplayName("06 Logging out returns to login and blocks the dashboard")
    void logOutBlocksDashboard() {
        loginAsDemo();
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logOut();
        WaitUtils.waitUrlContains(driver, "/login");

        driver.get(Config.BASE_URL + "/dashboard");
        WaitUtils.waitUrlContains(driver, "/login");
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
