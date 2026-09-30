# Holdings Sandbox: Selenium regression suite

A Java 17 + Selenium 4 + JUnit 5 suite of 15 tests for the Holdings Sandbox web app. This is the "before" half of the capstone: your job is to convert it to Playwright with TypeScript.

## What you need

* JDK 17 or newer (`java -version`)
* Maven 3.9 or newer (`mvn -version`)
* Google Chrome
* The Holdings Sandbox running (see below)

Selenium downloads the matching ChromeDriver by itself the first time it runs, so the machine needs internet access on that first run.

## Start the app

In the `holdings-sandbox` folder:

```
npm run dev
```

The site is at http://localhost:5173 and the API at http://localhost:4000.

## Run the suite

```
mvn test
```

Add `-Dheadless=true` to run without a visible browser. Run a single class with `mvn test -Dtest=SellTests`.

The suite changes the shared `demo` account (it sells a few units). If a sell test starts failing after many runs, restart the Sandbox to reset the data.

## What is in here

```
src/test/java/com/example/holdings/
  config/      run-time settings (URLs, headless)
  driver/      creates the Chrome driver
  pages/       page objects (Login, Dashboard, Sell, Confirmation, Statement)
  api/         direct API calls used for setup and checks
  util/        wait helpers
  tests/       the 15 tests
```

## The 15 tests

| # | Test |
|---|---|
| 01 | Valid login lands on the dashboard |
| 02 | Wrong password shows an error |
| 03 | Every business area can log in (three data rows) |
| 04 | Dashboard lists the holdings for the account |
| 05 | An account with no holdings shows the empty message |
| 06 | Logging out returns to login and blocks the dashboard |
| 07 | Selling on NSE with cash settlement is confirmed |
| 08 | Cheque settlement without a branch is rejected |
| 09 | Cheque settlement with a branch is confirmed |
| 10 | Selling more than is held is rejected |
| 11 | A sale reduces the quantity held on the server |
| 12 | A freshly seeded account starts with the default holdings and balance |
| 13 | The statement opens in a new window |
| 14 | The terms can be accepted inside the iframe |
| 15 | The market filter accepts several selections |
