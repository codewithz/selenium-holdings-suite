package com.example.holdings.pages;

import com.example.holdings.config.Config;
import com.example.holdings.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final By areaSelect = By.id("area");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("//p[@role='alert']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.BASE_URL + "/login");
        return this;
    }

    public void login(String area, String username, String password) {
        // The business-area list is filled in by an API call, so wait for it.
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> new Select(d.findElement(areaSelect)).getOptions().size() > 0);
        new Select(driver.findElement(areaSelect)).selectByVisibleText(area);
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String errorText() {
        return textOf(errorMessage);
    }

    public void waitForDashboard() {
        WaitUtils.waitUrlContains(driver, "/dashboard");
    }
}
