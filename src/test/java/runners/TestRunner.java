package runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
    features = "src/test/resources/Features", // Path to feature files
    glue = "stepDefinitions", // Path to step definition classes
    plugin = {"pretty", "html:target/cucumber-report.html"} // Report options
)
public class TestRunner extends AbstractTestNGCucumberTests {
}