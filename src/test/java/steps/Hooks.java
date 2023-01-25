package steps;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import runner.TestRunner;

public class Hooks {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Logger log = Logger.getLogger(String.valueOf(Hooks.class));
    Scenario scenario = null;

    @Before
    public void initDriver(Scenario scenario) throws IOException {
        log.info(
                "***********************************************************************************************************");
        log.info("[ Configuration ] - Initializing driver configuration");
        log.info(
                "***********************************************************************************************************");

        String browser = TestRunner.BROWSER.get();
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(false);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(false);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(false);
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                break;
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.scenario = scenario;
        log.info("[ Scenario ] - " + scenario.getName());
        log.info(
                "***********************************************************************************************************");
        log.info("[ Scenario ] - " + scenario.getName());
        log.info(
                "***********************************************************************************************************");
    }

    @After
    /** Embed a screenshot in test report if test is marked as failed */
    public void tearDown(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            BasePage base = new BasePage();
            base.getByteScreenshot();
        }
        log.info(
                "***********************************************************************************************************");
        log.info("[ Driver Status ] - Clean and close the intance of the driver");
        log.info(
                "***********************************************************************************************************");
        driver.quit();
    }
}
