package pages;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasePage {
    protected static WebDriver driver;
    private static WebDriverWait wait;

    /*@BeforeClass
    public void SetUp() {

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

        //driver = new ChromeDriver();
    }*/

    /*@AfterClass
    public void tearDown() {
        driver.close();
    }*/

    public static void closeBrowser() {
        driver.quit();
    }

    static {
        DesiredCapabilities caps = new DesiredCapabilities();
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

}
