package com.example.holdings.api;

import com.example.holdings.config.Config;
import org.openqa.selenium.json.Json;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/** Direct calls to the Sandbox API, used to set up and check data without the browser. */
public final class ApiClient {

    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static final Json JSON = new Json();

    private ApiClient() {
    }

    public record SeededAccount(String accountId, String username, String password) {
    }

    public static SeededAccount seedAccount() {
        Map<String, Object> body = post("/test/seed-account", "{}");
        return new SeededAccount(
                String.valueOf(body.get("accountId")),
                String.valueOf(body.get("username")),
                String.valueOf(body.get("password")));
    }

    public static String login(String area, String username, String password) {
        String payload = String.format(
                "{\"area\":\"%s\",\"username\":\"%s\",\"password\":\"%s\"}", area, username, password);
        return String.valueOf(post("/login", payload).get("accountId"));
    }

    @SuppressWarnings("unchecked")
    public static int holdingQuantity(String accountId, String holdingName) {
        Map<String, Object> body = get("/account/" + accountId);
        Map<String, Object> account = (Map<String, Object>) body.get("account");
        for (Map<String, Object> holding : (List<Map<String, Object>>) account.get("holdings")) {
            if (holdingName.equals(holding.get("name"))) {
                return ((Number) holding.get("quantity")).intValue();
            }
        }
        throw new IllegalStateException("No holding named " + holdingName);
    }

    private static Map<String, Object> get(String path) {
        return send(HttpRequest.newBuilder(URI.create(Config.API_URL + path)).GET().build());
    }

    private static Map<String, Object> post(String path, String json) {
        return send(HttpRequest.newBuilder(URI.create(Config.API_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build());
    }

    private static Map<String, Object> send(HttpRequest request) {
        try {
            HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new IllegalStateException(
                        request.uri() + " returned " + response.statusCode() + ": " + response.body());
            }
            return JSON.toType(response.body(), Json.MAP_TYPE);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("API call failed: " + request.uri(), e);
        }
    }
}
