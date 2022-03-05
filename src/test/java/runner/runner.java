package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import pages.BasePage;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"steps"},
        tags = {"@demoblaze"}
)

public class runner {
    @AfterClass
    public static void cleanDriver() {
        BasePage.closeBrowser();
    }
}