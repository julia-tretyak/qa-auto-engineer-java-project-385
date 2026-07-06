package hexlet.code.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By appBar = By.cssSelector("header");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAppBarDisplayed() {
        try {
            return driver.findElement(appBar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Jane Doe')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='menuitem' and contains(.,'Logout')]"))).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
