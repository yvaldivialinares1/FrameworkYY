package pages;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.RestAssuredExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasePage {
    protected static WebDriver driver;
    private static WebDriverWait wait;
    public static DataTable data;
    public static Map<String, String> scenarioData = new HashMap<>();

    @Before
    public void SetUp() {

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }
//    @AfterStep
//    public void addScreenshot(Scenario scenario){
//
//        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        scenario.attach(screenshot, "image/png", "image");
//
//    }

    public static void closeBrowser() {
        driver.quit();
    }

    static {
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 10);
        System.setProperty("webdriver.chrome.driver",
                "/src/test/lib/chromedriver.exe");
    }

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void explicitWait(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
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
                                    VALUES = RestAssuredExtension.response.getBody().jsonPath().get(rValue.get(0)).toString();
                                    // SAVE
                                    saveInScenarioContext(KEY, VALUES);
                                } catch (NullPointerException e) {
                                    System.out.println(String.format("Path specified doesn't exist: %s", VALUES));
                                }
                            });
        }
    }

    public DataTable createDataTable(List<List<String>> table) {
        data = DataTable.create(table);
        System.out.println(data);
        return data;
    }

    public void saveInScenarioContext(String key, String text) {
        try {
            if (!scenarioData.containsKey(key)) {
                scenarioData.put(key, text);
                System.out.println(String.format("Save as Scenario Context key: %s with value: %s ", key, text));
            } else {
                scenarioData.replace(key, text);
                System.out.println(String.format("Update Scenario Context key: %s with value: %s ", key, text));
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

}
