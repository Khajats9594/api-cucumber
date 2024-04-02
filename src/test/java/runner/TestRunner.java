package runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "src/test/resources/features",
                 plugin = {"pretty","json:target/cucumber.json","html:target/site/cucumber-pretty.html"},
                 glue = "stepDefinition")
public class TestRunner extends AbstractTestNGCucumberTests {
}
