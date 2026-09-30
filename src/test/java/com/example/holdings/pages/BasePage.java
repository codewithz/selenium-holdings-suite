package com.example.holdings.pages;

import com.example.holdings.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void click(By locator) {
        WaitUtils.waitClickable(driver, locator).click();
    }

    protected void type(By locator, String text) {
        WebElement field = WaitUtils.waitVisible(driver, locator);
        field.clear();
        field.sendKeys(text);
    }

    protected String textOf(By locator) {
        return WaitUtils.waitVisible(driver, locator).getText();
    }
}
