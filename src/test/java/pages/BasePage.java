package pages;

import io.cucumber.datatable.DataTable;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.Hooks;
import utils.RestAssuredExtension;

public class BasePage {
    protected static WebDriver driver;
    private static WebDriverWait wait;
    public static DataTable data;
    public static Map<String, String> scenarioData = new HashMap<>();
    Logger log;

    public BasePage() {
        driver = Hooks.driver;
        wait = Hooks.wait;
        log = Hooks.log;
    }

    public void explicitWait(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public boolean waitVisibility(By by, String wt) {
        int time;
        if (wt != null | wt != "") {
            time = Integer.valueOf(wt);
        } else {
            time = 10;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("===> Element: " + by + " is not present ===");
            return false;
        }
    }

    public void invisibleWait(By by) {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(by)));
    }

    public static void navigateTo(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    private WebElement Find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickElement(By locator) {
        Find(locator).click();
    }

    public void write(By locator, String textToWrite) {
        Find(locator).clear();
        Find(locator).sendKeys(textToWrite);
    }

    public boolean verifyAlert(String alertName) {
        wait.until(ExpectedConditions.alertIsPresent());
        boolean result = driver.switchTo().alert().getText().contains(alertName);
        driver.switchTo().alert().accept();
        return result;
    }

    public boolean verifyVisibleText(By locator, String textToCompare) {
        return Find(locator).getText().equals(textToCompare);
    }

    public boolean verifyInvisibleText(By locator) {
        invisibleWait(locator);
        return true;
    }

    public void clickOnSelectedObjet(By locator, String ObjetcName) {
        List<WebElement> elementList = driver.findElements(locator);
        for (int i = 0; i < elementList.size(); i++) {
            if (elementList.get(i).getText().equalsIgnoreCase(ObjetcName)) {
                elementList.get(i).click();
                break;
            }
        }
    }

    public boolean verifyTheItemIsDisplayed(By locator, String itemName) {
        boolean result = false;
        List<WebElement> elementList = driver.findElements(locator);
        for (int i = 0; i < elementList.size(); i++) {
            if (elementList.get(i).getText().equalsIgnoreCase(itemName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void getDataFromApiServices(String path, String body, List<List<String>> t_table) {
        RestAssuredExtension.response = RestAssuredExtension.postMethod(path, body);
        DataTable data = createDataTable(t_table);
        if (data != null) {
            AtomicInteger i = new AtomicInteger(1);
            data.cells()
                    .forEach(
                            value -> {
                                List<String> rField = Collections.singletonList(value.get(0));
                                List<String> rValue = Collections.singletonList(value.get(1));
                                String VALUES = null;
                                try {
                                    String KEY = rField.get(0);
                                    VALUES =
                                            RestAssuredExtension.response
                                                    .getBody()
                                                    .jsonPath()
                                                    .get(rValue.get(0))
                                                    .toString();
                                    // SAVE
                                    saveInScenarioContext(KEY, VALUES);
                                } catch (NullPointerException e) {
                                    log.info(String.format("Path specified doesn't exist: %s", VALUES));
                                }
                            });
        }
    }

    public DataTable createDataTable(List<List<String>> table) {
        data = DataTable.create(table);
        log.info(String.valueOf(data));
        return data;
    }

    public void saveInScenarioContext(String key, String text) {
        try {
            if (!scenarioData.containsKey(key)) {
                scenarioData.put(key, text);
                log.info(String.format("Save as Scenario Context key: %s with value: %s ", key, text));
            } else {
                scenarioData.replace(key, text);
                log.info(String.format("Update Scenario Context key: %s with value: %s ", key, text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getScenarioContextVariables(String key) {
        try {
            return scenarioData.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
    @Attachment(value = "screenshot_error", type = "image/png")
    public byte[] getByteScreenshot() throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(src);

        return fileContent;
    }

}
