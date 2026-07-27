package hexlet.code.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class WaitUtils {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration POLLING = Duration.ofMillis(500);

    public static FluentWait<WebDriver> getWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(TIMEOUT)
                .pollingEvery(POLLING)
                .ignoring(Exception.class)
                .withMessage("Element not found within timeout");
    }

    public static WebElement waitForElement(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForTableRows(WebDriver driver) {
        getWait(driver).until(d -> {
            int rowCount = d.findElements(By.cssSelector(".RaDatagrid-row")).size();
            return rowCount > 0 || !d.findElements(By.cssSelector(".RaList-noResults")).isEmpty();
        });
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
