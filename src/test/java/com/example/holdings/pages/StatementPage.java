package com.example.holdings.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class StatementPage extends BasePage {

    private final By heading = By.tagName("h1");
    private final By rows = By.cssSelector("[data-testid='statement-list'] li");
    private final By marketFilter = By.id("market-filter");
    private final By termsFrame = By.cssSelector("iframe[title='Statement terms']");
    private final By acceptButton = By.id("accept");
    private final By acceptResult = By.id("result");

    public StatementPage(WebDriver driver) {
        super(driver);
    }

    public String headingText() {
        return textOf(heading);
    }

    public int rowCount() {
        return driver.findElements(rows).size();
    }

    public String rowsText() {
        return textOf(By.cssSelector("[data-testid='statement-list']"));
    }

    public Select marketFilter() {
        return new Select(driver.findElement(marketFilter));
    }

    public String acceptTermsInFrame() {
        WebElement frame = driver.findElement(termsFrame);
        driver.switchTo().frame(frame);
        click(acceptButton);
        String result = textOf(acceptResult);
        driver.switchTo().defaultContent();
        return result;
    }
}
