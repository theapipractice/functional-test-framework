package suite;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;
import flowstep.business.datacontext.TestContext;
import flowstep.business.enums.Context;

@CucumberOptions(
        dryRun = false,//Skip execution of glue code.
        strict = true,// Treat undefined and pending steps as errors.
        features = "src/test/resources/features",
        glue = {"suite.stepdefs"},
        tags = {"@smoke"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber-report.json"},
        monochrome = true)

public class TestRunner  {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    @Parameters("browserType")
    public void setUpClass(@Optional String browserType){
        TestContext.getScenarioContext().setContext(Context.BROWSER_NAME, browserType);
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = {"cucumber"}, description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass()  {
        testNGCucumberRunner.finish();
    }
}

