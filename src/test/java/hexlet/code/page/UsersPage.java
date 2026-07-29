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

public class UsersPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String baseUrl;

    public UsersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String baseUrl) {
        this.baseUrl = baseUrl;
        driver.get(baseUrl + "/#/users");
        WaitUtils.waitForElement(driver, By.cssSelector(".RaDatagrid-root"));
    }

    public void goToList() {
        WaitUtils.waitForClickable(driver,
                By.xpath("//a[contains(@href, '#/users') and contains(@class, 'MuiMenuItem-root')]"))
                .click();
        WaitUtils.waitForTableRows(driver);
    }

    public void clickCreate() {
        WaitUtils.waitForClickable(driver, By.cssSelector(".RaCreateButton-root")).click();
    }

    public void fillFirstName(String firstName) {
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='firstName']")).clear();
        driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='lastName']")).clear();
        driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys(lastName);
    }

    public void fillEmail(String email) {
        WaitUtils.waitForElement(driver, By.cssSelector("input[name='email']")).clear();
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
    }

    public void clickSave() {
        WaitUtils.waitForClickable(driver, By.cssSelector("button[aria-label='Save']")).click();
    }

    public void createUser(String firstName, String lastName, String email) {
        clickCreate();
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        clickSave();
        goToList();
    }

    public boolean isUserInList(String name) {
        WaitUtils.waitForTableRows(driver);
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) return true;
        }
        return false;
    }

    public int getUserCount() {
        if (!driver.findElements(By.cssSelector(".RaList-noResults")).isEmpty()) return 0;
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        int count = 0;
        for (WebElement row : rows) {
            String text = row.getText();
            if (!text.isEmpty() && !text.startsWith("Id") && !text.startsWith("Email")) count++;
        }
        return count;
    }

    public void clickEditUser(String name) {
        goToList();
        List<WebElement> rows = driver.findElements(By.cssSelector(".RaDatagrid-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) {
                row.findElement(By.cssSelector(".column-firstName")).click();
                WaitUtils.waitForElement(driver, By.cssSelector("input[name='firstName']"));
                return;
            }
        }
    }

    public String getFirstNameValue() {
        return driver.findElement(By.cssSelector("input[name='firstName']")).getAttribute("value");
    }

    public String getLastNameValue() {
        return driver.findElement(By.cssSelector("input[name='lastName']")).getAttribute("value");
    }

    public String getEmailValue() {
        return driver.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
    }

    public void editAndSave(String newFirstName, String newLastName) {
        fillFirstName(newFirstName);
        fillLastName(newLastName);
        clickSave();
        goToList();
    }

    public void deleteUser(String name) {
        clickEditUser(name);
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".ra-delete-button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[text()='Delete']")));
        WebElement confirmBtn = driver.findElement(By.xpath("//button[text()='Delete']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
        goToList();
    }

    public void selectAllUsers() {
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

    public boolean hasValidationError() {
        return driver.getPageSource().contains("not valid") || driver.getPageSource().contains("Invalid email");
    }
}
