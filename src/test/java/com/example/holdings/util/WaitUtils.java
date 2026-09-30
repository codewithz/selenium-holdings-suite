package com.example.holdings.util;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public final class WaitUtils {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private WaitUtils() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static WebElement waitVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitUrlContains(WebDriver driver, String fragment) {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.urlContains(fragment));
    }

    /** The page re-renders while we read it, so retry a few times before giving up. */
    public static <T> T retryOnStale(Supplier<T> action) {
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                return action.get();
            } catch (StaleElementReferenceException e) {
                sleep(500);
            }
        }
        return action.get();
    }
}
