package hexlet.code.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StatusesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String baseUrl;

    public StatusesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String baseUrl) {
        this.baseUrl = baseUrl;
        driver.get(baseUrl + "/#/task_statuses");
        waitForPage();
    }

    private void waitForPage() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".RaDatagrid-root")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".RaList-noResults")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body"))
        ));
    }

    public void goToList() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '#/task_statuses') and contains(@class, 'MuiMenuItem-root')]"))).click();
        waitForPage();
    }

    public void clickCreate() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".RaCreateButton-root"))).click();
    }

    public void fillName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']"))).clear();
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys(name);
    }

    public void fillSlug(String slug) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='slug']"))).clear();
        driver.findElement(By.cssSelector("input[name='slug']")).sendKeys(slug);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Save']"))).click();
    }

    public void createStatus(String name, String slug) {
        clickCreate();
        fillName(name);
        fillSlug(slug);
        clickSave();
        goToList();
    }

    public boolean isStatusInList(String name) {
        try {
            Thread.sleep(500);
            List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
            for (WebElement row : rows) {
                if (row.getText().contains(name)) return true;
            }
        } catch (Exception e) { return false; }
        return false;
    }

    public int getStatusCount() {
        if (!driver.findElements(By.cssSelector(".RaList-noResults")).isEmpty()) {
            return 0;
        }
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        int count = 0;
        for (WebElement row : rows) {
            String text = row.getText();
            if (!text.isEmpty() && !text.startsWith("Id") && !text.startsWith("Name")) count++;
        }
        return count;
    }

    public void clickEditStatus(String name) {
        goToList();
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) {
                row.findElement(By.cssSelector(".column-name")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
                return;
            }
        }
    }

    public String getNameValue() {
        return driver.findElement(By.cssSelector("input[name='name']")).getAttribute("value");
    }

    public String getSlugValue() {
        return driver.findElement(By.cssSelector("input[name='slug']")).getAttribute("value");
    }

    public void editAndSave(String newName, String newSlug) {
        fillName(newName);
        fillSlug(newSlug);
        clickSave();
        goToList();
    }

    public void deleteStatus(String name) {
        clickEditStatus(name);
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ra-delete-button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Delete']")));
        WebElement confirmBtn = driver.findElement(By.xpath("//button[text()='Delete']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
        goToList();
    }

    public void selectAllStatuses() {
        WebElement checkboxSpan = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".select-all")));
        new Actions(driver).moveToElement(checkboxSpan).pause(200).click().pause(200).perform();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public void clickBulkDelete() {
        WebElement toolbar = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-test='bulk-actions-toolbar']")));
        WebElement deleteBtn = toolbar.findElement(By.cssSelector("button[aria-label='Delete']"));
        new Actions(driver).moveToElement(deleteBtn).pause(200).click().perform();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        goToList();
    }
}
