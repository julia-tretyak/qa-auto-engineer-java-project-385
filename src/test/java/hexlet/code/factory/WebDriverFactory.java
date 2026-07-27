package hexlet.code.factory;

import hexlet.code.config.CiConfig;
import hexlet.code.config.LocalConfig;
import hexlet.code.config.TestConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverFactory {

    public static WebDriver create() {
        TestConfig config = resolveConfig();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        } else {
            options.addArguments("--remote-allow-origins=*");
        }

        WebDriver driver = new ChromeDriver(options);

        if (config.isHeadless()) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }

    private static TestConfig resolveConfig() {
        if (System.getenv("APP_BASE_URL") != null) {
            return new CiConfig();
        }
        return new LocalConfig();
    }
}
