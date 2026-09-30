package com.example.holdings.tests;

import com.example.holdings.api.ApiClient;
import com.example.holdings.pages.ConfirmationPage;
import com.example.holdings.pages.DashboardPage;
import com.example.holdings.pages.SellPage;
import com.example.holdings.util.WaitUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SellTests extends BaseTest {

    private DashboardPage dashboard;
    private SellPage sellPage;
    private ConfirmationPage confirmation;

    @BeforeEach
    void logInAndOpenSellForm() {
        loginAsDemo();
        dashboard = new DashboardPage(driver);
        sellPage = new SellPage(driver);
        confirmation = new ConfirmationPage(driver);
        dashboard.clickSell(1);
    }

    @Test
    @DisplayName("07 Selling on NSE with cash settlement is confirmed")
    void sellCashOnNse() {
        sellPage.chooseMarket("NSE");
        sellPage.chooseSettlement("Cash");
        sellPage.enterQuantity("1");
        sellPage.confirmDetails();
        sellPage.submit();

        WaitUtils.sleep(2000);
        assertEquals("Transaction submitted and under process", confirmation.headingText());
        assertEquals("processing", confirmation.statusText());
        assertEquals("cash", confirmation.settlementText());
    }

    @Test
    @DisplayName("08 Cheque settlement without a branch is rejected")
    void chequeNeedsBranch() {
        sellPage.chooseSettlement("Cheque");
        sellPage.enterQuantity("1");
        sellPage.confirmDetails();
        sellPage.submit();

        assertEquals("Cheque settlement requires a branch", sellPage.errorText());
        assertTrue(driver.getCurrentUrl().contains("/sell/"));
    }

    @Test
    @DisplayName("09 Cheque settlement with a branch is confirmed")
    void chequeWithBranchConfirms() {
        sellPage.chooseMarket("NSE");
        sellPage.chooseSettlement("Cheque");
        sellPage.enterChequeBranch("Fort, Mumbai");
        sellPage.enterQuantity("1");
        sellPage.confirmDetails();
        sellPage.submit();

        WaitUtils.sleep(2000);
        assertEquals("cheque", confirmation.settlementText());
        assertEquals("processing", confirmation.statusText());
    }

    @Test
    @DisplayName("10 Selling more than is held is rejected")
    void oversellIsRejected() {
        sellPage.enterQuantity("9999");
        sellPage.confirmDetails();
        sellPage.submit();

        String message = sellPage.errorText();
        assertTrue(message.startsWith("Only "));
        assertTrue(message.endsWith(" units available to sell"));
    }

    @Test
    @DisplayName("11 A sale reduces the quantity held on the server")
    void saleReducesQuantityOnServer() {
        String accountId = ApiClient.login("Retail Banking", "demo", "demo1234");
        int before = ApiClient.holdingQuantity(accountId, "Bluechip Growth Fund");

        sellPage.enterQuantity("2");
        sellPage.confirmDetails();
        sellPage.submit();
        confirmation.headingText();

        int after = ApiClient.holdingQuantity(accountId, "Bluechip Growth Fund");
        assertEquals(before - 2, after);
    }
}
