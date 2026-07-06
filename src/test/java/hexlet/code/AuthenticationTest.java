package hexlet.code;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        loginPage.open(baseUrl);
        assertTrue(loginPage.isLoginFormDisplayed(),
                "Login form should be displayed before login");

        loginPage.login("admin", "admin");

        assertTrue(mainPage.isAppBarDisplayed(),
                "App bar should be displayed after successful login");
    }

    @Test
    public void testLogout() {
        loginPage.open(baseUrl);
        loginPage.login("test", "test");

        assertTrue(mainPage.isAppBarDisplayed(),
                "Should be logged in before testing logout");

        mainPage.logout();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login"));

        assertTrue(loginPage.isLoginFormDisplayed(),
                "Login form should be displayed after logout");
    }

    @Test
    public void testLoginWithDifferentCredentials() {
        loginPage.open(baseUrl);
        loginPage.login("user123", "password123");

        assertTrue(mainPage.isAppBarDisplayed(),
                "Should be able to login with any credentials");
    }
}
