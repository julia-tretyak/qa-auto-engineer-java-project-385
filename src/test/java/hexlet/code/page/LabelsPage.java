package hexlet.code.page;

import hexlet.code.util.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LabelsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String baseUrl;

    public LabelsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String baseUrl) {
        this.baseUrl = baseUrl;
        driver.get(baseUrl + "/#/labels");
        WaitUtils.waitForElement(driver, By.cssSelector(".RaDatagrid-root"));
    }

    public void goToList() {
        WaitUtils.waitForClickable(driver,
                By.xpath("//a[contains(@href, '#/labels') and contains(@class, 'MuiMenuItem-root')]"))
                .click();
        WaitUtils.waitForTableRows(driver);
    }

    public void clickCreate() {
        WaitUtils.waitForClickable(driver, By.cssSelector(".RaCreateButton-root")).click();
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='name']"));
    }

    public void fillName(String name) {
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='name']")).clear();
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys(name);
    }

    public void clickSave() {
        WaitUtils.waitForClickable(driver, By.cssSelector("button[aria-label='Save']")).click();
    }

    public void createLabel(String name) {
        clickCreate();
        fillName(name);
        clickSave();
        goToList();
    }

    public boolean isLabelInList(String name) {
        WaitUtils.waitForTableRows(driver);
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) return true;
        }
        return false;
    }

    public int getLabelCount() {
        if (!driver.findElements(By.cssSelector(".RaList-noResults")).isEmpty()) return 0;
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        int count = 0;
        for (WebElement row : rows) {
            String text = row.getText();
            if (!text.isEmpty() && !text.startsWith("Id") && !text.startsWith("Name")) count++;
        }
        return count;
    }

    public void clickEditLabel(String name) {
        goToList();
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) {
                row.findElement(By.cssSelector(".column-name")).click();
                WaitUtils.waitForElement(driver, By.cssSelector("input[name='name']"));
                return;
            }
        }
    }

    public String getNameValue() {
        return driver.findElement(By.cssSelector("input[name='name']")).getAttribute("value");
    }

    public void editAndSave(String newName) {
        fillName(newName);
        clickSave();
        goToList();
    }

    public void deleteLabel(String name) {
        clickEditLabel(name);
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".ra-delete-button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[text()='Delete']")));
        WebElement confirmBtn = driver.findElement(By.xpath("//button[text()='Delete']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
        goToList();
    }

    public void selectAllLabels() {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".select-all")));
        new Actions(driver).moveToElement(checkbox).pause(200).click().perform();
        WaitUtils.waitForAnimation(driver);
    }

    public void clickBulkDelete() {
        WaitUtils.waitForAnimation(driver);
        WebElement toolbar = driver.findElement(By.cssSelector("[data-test='bulk-actions-toolbar']"));
        WebElement deleteBtn = toolbar.findElement(By.cssSelector("button[aria-label='Delete']"));
        new Actions(driver).moveToElement(deleteBtn).pause(200).click().perform();
        WaitUtils.waitForAnimation(driver);
        goToList();
    }
}
