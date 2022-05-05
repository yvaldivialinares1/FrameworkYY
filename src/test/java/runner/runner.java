package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    // plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
    // monochrome = true,
    glue = {"classpath:steps"},
    tags = "@regression")
public class runner {}
