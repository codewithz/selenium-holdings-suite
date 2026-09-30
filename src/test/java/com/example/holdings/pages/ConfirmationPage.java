package com.example.holdings.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage {

    private final By heading = By.cssSelector("[data-testid='confirmation-heading']");
    private final By status = By.cssSelector("[data-testid='transaction-status']");
    private final By settlement = By.xpath("//dt[text()='Settlement']/following-sibling::dd[1]");
    private final By quantity = By.xpath("//dt[text()='Quantity']/following-sibling::dd[1]");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String headingText() {
        return textOf(heading);
    }

    public String statusText() {
        return textOf(status);
    }

    public String settlementText() {
        return textOf(settlement);
    }

    public String quantityText() {
        return textOf(quantity);
    }
}
