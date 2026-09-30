package com.example.holdings.config;

/** Run-time settings, all overridable with -D flags, e.g. mvn test -Dheadless=true */
public final class Config {

    public static final String BASE_URL = System.getProperty("base.url", "http://localhost:5173");
    public static final String API_URL = System.getProperty("api.url", "http://localhost:4000/api");
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "false"));

    /** Optional: path to a specific Chrome binary. Leave unset to use the installed Chrome. */
    public static final String CHROME_BINARY = System.getProperty("chrome.binary");

    /** Needed only inside some containers. */
    public static final boolean NO_SANDBOX = Boolean.parseBoolean(System.getProperty("chrome.nosandbox", "false"));

    private Config() {
    }
}
