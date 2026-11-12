package com.poc;

import static org.junit.jupiter.api.Assertions.*;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Regression gate for the POC. After Devin upgrades libraries the app must
 * still show the login page and, after login, the Hello World page.
 */
class AppTest {

    static App app;
    static String base;
    HttpClient client = HttpClient.newBuilder()
            .cookieHandler(new CookieManager())
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    @BeforeAll
    static void start() throws Exception {
        app = new App();
        base = "http://localhost:" + app.start(0);
    }

    @AfterAll
    static void stop() throws Exception {
        app.stop();
    }

    private HttpResponse<String> get(String path) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create(base + path)).GET().build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> login(String user, String pass) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create(base + "/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("username=" + user + "&password=" + pass))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void loginPageLoads() throws Exception {
        HttpResponse<String> r = get("/login");
        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Login"));
    }

    @Test
    void helloRequiresLogin() throws Exception {
        assertEquals(302, get("/hello").statusCode());
    }

    @Test
    void wrongPasswordStaysOnLogin() throws Exception {
        HttpResponse<String> r = login("admin", "wrong");
        assertEquals(302, r.statusCode());
        assertTrue(r.headers().firstValue("Location").orElse("").contains("/login"));
    }

    @Test
    void loginThenHelloWorld() throws Exception {
        HttpResponse<String> r = login("admin", "admin123");
        assertEquals(302, r.statusCode());
        assertTrue(r.headers().firstValue("Location").orElse("").endsWith("/hello"));
        HttpResponse<String> hello = get("/hello");
        assertEquals(200, hello.statusCode());
        assertTrue(hello.body().contains("Hello World"));
    }

    @Test
    void healthReturnsJson() throws Exception {
        HttpResponse<String> r = get("/health");
        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("UP"));
    }
}
