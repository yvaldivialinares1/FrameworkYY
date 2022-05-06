package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--start-maximized");
//    options.addArguments("--window-size=1920, 1080");
//    options.addArguments("--disable-infobars");
    options.addArguments("start-maximized"); // open Browser in maximized mode
    options.addArguments("disable-infobars"); // disabling infobars
    options.addArguments("--disable-extensions"); // disabling extensions
    options.addArguments("--disable-gpu"); // applicable to windows os only
    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
    options.addArguments("--no-sandbox"); // Bypass OS security model
    
    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, 10);
    this.scenario = scenario;
    log.info(
        "***********************************************************************************************************");
    log.info("[ Scenario ] - " + scenario.getName());
    log.info(
        "***********************************************************************************************************");
  }

  @After
  public void closeDriver() {
    log.info(
        "***********************************************************************************************************");
    log.info("[ Driver Status ] - Clean and close the intance of the driver");
    log.info(
        "***********************************************************************************************************");
    driver.quit();
  }
}
