package hexlet.code.factory;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class);

    public static WebDriver create() {
        log.info("Creating WebDriver");

        boolean isCi = System.getenv("APP_BASE_URL") != null;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (isCi) {
            options.addArguments("--headless");
        } else {
            options.addArguments("--remote-allow-origins=*");
        }

        WebDriver driver = new ChromeDriver(options);

        if (isCi) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        log.info("WebDriver created successfully");
        return driver;
    }
}
