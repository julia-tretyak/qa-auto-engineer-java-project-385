package hexlet.code;

import hexlet.code.config.LocalConfig;
import hexlet.code.config.TestConfig;
import hexlet.code.factory.WebDriverFactory;
import hexlet.code.page.LoginPage;
import hexlet.code.page.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected LoginPage loginPage;
    protected MainPage mainPage;

    @RegisterExtension
    final TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            takeScreenshot(context);
        }
    };

    private void takeScreenshot(ExtensionContext context) {
        if (driver instanceof TakesScreenshot) {
            String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "build/screenshots/" + testName + "_" + timestamp + ".png";
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Path targetPath = Path.of(filename);
                Files.createDirectories(targetPath.getParent());
                Files.copy(screenshot.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot saved: " + filename);
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @BeforeEach
    public void setupTest() {
        TestConfig config = new LocalConfig();
        baseUrl = config.getBaseUrl();

        driver = WebDriverFactory.create();

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
