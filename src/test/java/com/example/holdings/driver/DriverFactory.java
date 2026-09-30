package com.example.holdings.driver;

import com.example.holdings.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createChrome() {
        ChromeOptions options = new ChromeOptions();
        if (Config.CHROME_BINARY != null) {
            options.setBinary(Config.CHROME_BINARY);
        }
        if (Config.HEADLESS) {
            options.addArguments("--headless=new");
        }
        if (Config.NO_SANDBOX) {
            options.addArguments("--no-sandbox", "--disable-gpu");
        }
        options.addArguments("--window-size=1280,900");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
