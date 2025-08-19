package repositories.repo1.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.*;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("fakeStore/features") // Points to src/test/resources/fakeStore/features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "repositories.repo1.cucumber.fakeStore.steps") // Specifies step definitions package
//@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/report.html, json:target/cucumber-reports/report.json")
//@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @wip")
public class CucumberRunner {
    // This class remains empty. It's a hook for JUnit 5 to discover and run tests.
}
