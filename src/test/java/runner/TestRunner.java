package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "classpath:features",
        glue = {"classpath:steps"},
        tags = "@regression")
public class TestRunner extends AbstractTestNGCucumberTests {
    public final static ThreadLocal<String> BROWSER = new ThreadLocal<>();

    @BeforeTest
    @Parameters("browser")
    public void defineBrowser(String browser) {
        TestRunner.BROWSER.set(browser);
    }
}
