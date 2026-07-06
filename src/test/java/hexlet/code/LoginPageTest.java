package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends BaseTest {

    @Test
    public void testLoginPageOpens() {
        loginPage.open(baseUrl);
        assertTrue(loginPage.isLoginFormDisplayed(),
                "Login form should be displayed when opening the app");
    }
}
