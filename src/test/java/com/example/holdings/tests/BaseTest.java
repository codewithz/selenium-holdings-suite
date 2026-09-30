package com.example.holdings.tests;

import com.example.holdings.config.Config;
import com.example.holdings.driver.DriverFactory;
import com.example.holdings.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected static WebDriver driver;

    @BeforeAll
    static void startBrowser() {
        driver = DriverFactory.createChrome();
    }

    @AfterAll
    static void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void clearBrowserState() {
        driver.get(Config.BASE_URL + "/login");
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
    }

    protected void loginAsDemo() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("Retail Banking", "demo", "demo1234");
        loginPage.waitForDashboard();
    }
}
