package hexlet.code;

import hexlet.code.page.LoginPage;
import hexlet.code.page.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected LoginPage loginPage;
    protected MainPage mainPage;
    protected static boolean driverAvailable = true;

    @BeforeAll
    public static void setupClass() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            new ChromeDriver(options).quit();
        } catch (Exception e) {
            driverAvailable = false;
            System.out.println("Chrome not available, all tests will be skipped");
        }
    }

    @BeforeEach
    public void setupTest() {
        org.junit.jupiter.api.Assumptions.assumeTrue(driverAvailable, 
                "Skipping test: Chrome browser not available");

        baseUrl = System.getenv("APP_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:5173";
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
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
