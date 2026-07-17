package hexlet.code.factory;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }
}
