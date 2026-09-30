package com.example.holdings.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SellPage extends BasePage {

    private final By quantityField = By.id("quantity");
    private final By chequeBranchField = By.id("chequeBranch");
    private final By confirmCheckbox = By.name("confirmed");
    private final By submitButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("//p[@role='alert']");

    public SellPage(WebDriver driver) {
        super(driver);
    }

    /** market is the visible label: "NSE" or "BSE". */
    public void chooseMarket(String market) {
        click(By.xpath("//label[normalize-space()='" + market + "']/input"));
    }

    /** settlement is the visible label: "Cash" or "Cheque". */
    public void chooseSettlement(String settlement) {
        click(By.xpath("//label[normalize-space()='" + settlement + "']/input"));
    }

    public void enterQuantity(String quantity) {
        type(quantityField, quantity);
    }

    public void enterChequeBranch(String branch) {
        type(chequeBranchField, branch);
    }

    public void confirmDetails() {
        click(confirmCheckbox);
    }

    public void submit() {
        click(submitButton);
    }

    public String errorText() {
        return textOf(errorMessage);
    }
}
