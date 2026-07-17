package hexlet.code.factory;

import hexlet.code.config.CiConfig;
import hexlet.code.config.LocalConfig;
import hexlet.code.config.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver create() {
        TestConfig config = getConfig();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (config.isHeadless()) {
            options.addArguments("--headless");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    private static TestConfig getConfig() {
        String ci = System.getenv("CI");
        if (ci != null && ci.equals("true")) {
            return new CiConfig();
        }
        return new LocalConfig();
    }
}
