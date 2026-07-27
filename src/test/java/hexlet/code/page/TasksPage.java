package hexlet.code.page;

import hexlet.code.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TasksPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String baseUrl;

    public TasksPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String baseUrl) {
        this.baseUrl = baseUrl;
        driver.get(baseUrl + "/#/tasks");
        WaitUtils.waitForElement(driver, By.cssSelector(".RaList-content"));
    }

    public void goToList() {
        WaitUtils.waitForClickable(driver,
                By.xpath("//a[contains(@href, '#/tasks') and contains(@class, 'MuiMenuItem-root')]"))
                .click();
        WaitUtils.waitForElement(driver, By.cssSelector(".RaList-content"));
    }

    public void clickCreate() {
        WaitUtils.waitForClickable(driver, By.cssSelector(".RaCreateButton-root")).click();
    }

    public void fillTitle(String title) {
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='title']")).clear();
        driver.findElement(By.cssSelector("input[name='title']")).sendKeys(title);
    }

    private void selectFromDropdown(int index) {
        List<WebElement> comboboxes = driver.findElements(By.cssSelector("[role='combobox']"));
        if (comboboxes.size() > index) {
            comboboxes.get(index).click();
            WaitUtils.waitForElement(driver, By.cssSelector("[role='listbox']"));
            List<WebElement> options = driver.findElements(By.cssSelector("[role='option']"));
            if (!options.isEmpty()) options.get(0).click();
        }
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[aria-label='Save']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
    }

    public void createTask(String title) {
        clickCreate();
        fillTitle(title);
        selectFromDropdown(0);
        selectFromDropdown(1);
        clickSave();
        goToList();
    }

    public boolean isTaskOnBoard(String title) {
        WaitUtils.waitForElement(driver, By.cssSelector(".MuiCard-root"));
        List<WebElement> cards = driver.findElements(By.cssSelector(".MuiCard-root"));
        for (WebElement card : cards) {
            if (card.getText().contains(title)) return true;
        }
        return false;
    }

    public int getTaskCount() {
        return driver.findElements(By.cssSelector(".MuiCard-root")).size();
    }

    public void clickEditTask(String title) {
        goToList();
        List<WebElement> cards = driver.findElements(By.cssSelector(".MuiCard-root"));
        for (WebElement card : cards) {
            if (card.getText().contains(title)) {
                card.findElement(By.cssSelector(".RaEditButton-root")).click();
                WaitUtils.waitForElement(driver, By.cssSelector("input[name='title']"));
                return;
            }
        }
    }

    public String getTitleValue() {
        return driver.findElement(By.cssSelector("input[name='title']")).getAttribute("value");
    }

    public void editAndSave(String newTitle) {
        fillTitle(newTitle);
        clickSave();
        goToList();
    }

    public void deleteTask(String title) {
        clickEditTask(title);
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".ra-delete-button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
        goToList();
    }
}
