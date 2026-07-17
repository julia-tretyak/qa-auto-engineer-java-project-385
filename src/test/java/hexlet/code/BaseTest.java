package hexlet.code;

import hexlet.code.config.LocalConfig;
import hexlet.code.config.TestConfig;
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
        TestConfig config = new LocalConfig();
        baseUrl = config.getBaseUrl();

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
