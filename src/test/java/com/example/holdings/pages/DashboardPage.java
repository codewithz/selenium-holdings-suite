package com.example.holdings.pages;

import com.example.holdings.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By welcomeHeading = By.xpath("//main//h1");
    private final By areaBadge = By.cssSelector(".area-badge");
    private final By holdingRows = By.xpath("//ul[@class='holdings-list']/li");
    private final By firstHoldingName = By.xpath("//ul[@class='holdings-list']/li[1]/div/p[1]");
    private final By firstHoldingMeta = By.xpath("//ul[@class='holdings-list']/li[1]/div/p[2]");
    private final By noHoldingsMessage = By.xpath("//p[contains(text(),'No holdings remaining')]");
    private final By balance = By.cssSelector("[data-testid='account-balance']");
    private final By logOutButton = By.xpath("//button[text()='Log Out']");
    private final By statementLink = By.linkText("View statement");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String welcomeText() {
        return textOf(welcomeHeading);
    }

    public String areaText() {
        return textOf(areaBadge);
    }

    public String balanceText() {
        return textOf(balance);
    }

    public int holdingCount() {
        WaitUtils.waitVisible(driver, welcomeHeading);
        return driver.findElements(holdingRows).size();
    }

    public String firstHoldingName() {
        return WaitUtils.retryOnStale(() -> textOf(firstHoldingName));
    }

    public String firstHoldingMeta() {
        return WaitUtils.retryOnStale(() -> textOf(firstHoldingMeta));
    }

    public String noHoldingsText() {
        return textOf(noHoldingsMessage);
    }

    /** position is 1-based: 1 = first holding on the page. */
    public void clickSell(int position) {
        click(By.xpath("(//a[@role='button'])[" + position + "]"));
    }

    public void logOut() {
        click(logOutButton);
    }

    public void openStatementLink() {
        click(statementLink);
    }
}
