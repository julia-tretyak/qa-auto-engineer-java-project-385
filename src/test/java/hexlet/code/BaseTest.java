package hexlet.code;

import hexlet.code.factory.WebDriverFactory;
import hexlet.code.page.LoginPage;
import hexlet.code.page.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected LoginPage loginPage;
    protected MainPage mainPage;

    @BeforeEach
    public void setupTest() {
        baseUrl = System.getenv("APP_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:5173";
        }

        driver = WebDriverFactory.create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
