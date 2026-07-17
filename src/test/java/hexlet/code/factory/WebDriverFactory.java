package hexlet.code.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless");

        return new ChromeDriver(options);
    }
}
