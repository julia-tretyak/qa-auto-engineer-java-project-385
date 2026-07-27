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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        log.info("WebDriver created successfully");
        return driver;
    }
}
