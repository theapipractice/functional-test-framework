package suite.stepdefs;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import common.config.Config;
import common.utils.Log;
import common.wdm.WDFactory;
import common.wdm.WdManager;
import com.google.common.io.Files;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import rga.utils.FlowChart;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ServiceHooks {
    @Before(order = 0)
    public void beforeScenario(){
        Config.loadEnvInfoToQueue();
    }

    @Before(order = 1)
    public void beforeSteps(Scenario scenario) throws MalformedURLException {
//        DesiredCapabilities desiredCapabilities = null;
//         switch (Config.getProp("browser")){
//            case "gc":
//                desiredCapabilities = new DesiredCapabilities();
//                desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
//                desiredCapabilities.setCapability("name", "Feature : " + scenario.getName() + " is run at " + Common.getCurrentTime());
//                WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
//                break;
//            case "ff":
//                desiredCapabilities = new DesiredCapabilities();
//                desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
//                desiredCapabilities.setCapability("name", scenario.getName());
//                WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
//                break;
//            case "ie" :
//                desiredCapabilities = new DesiredCapabilities();
//                desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.IE);
//                desiredCapabilities.setCapability("name", scenario.getName());
//                WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
//                break;
//       }

        WDFactory.getConfig().setDriverVersion("80");
        WdManager.set(WDFactory.initBrowser(Config.getProp("browser")));
        WdManager.get().get(Config.getProp("url"));
}

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        String screenshotName = scenario.getName().replaceAll(" ", "_");
        if (scenario.isFailed()) {
            try {
                //draw the flow chart
                FlowChart.flowChartName = screenshotName;
                FlowChart.addFlowChart(false, FlowChart.featureName, true);

                //This takes a screenshot from the driver at save it to the specified location
                File sourcePath = ((TakesScreenshot) WdManager.get()).getScreenshotAs(OutputType.FILE);

                //Building up the destination path for the screenshot to save
                //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");

                //Copy taken screenshot from source location to destination location
                Files.copy(sourcePath, destinationPath);

                //This attach the specified screenshot to the test
                String screenshotsPath = "./screenshots/" + screenshotName + ".png";
                Reporter.addScreenCaptureFromPath(screenshotsPath);

                //add result to zalenium
                Cookie cookie = new Cookie("zaleniumTestPassed", "false");
                WdManager.get().manage().addCookie(cookie);
            } catch (IOException e) {
                Log.error(e.getMessage());
            }
        }else {
            //draw the flow chart
            FlowChart.flowChartName = screenshotName;
            FlowChart.addFlowChart(true, "", true);

            //add result to zalenium
            Cookie cookie = new Cookie("zaleniumTestPassed", "true");
            WdManager.get().manage().addCookie(cookie);
        }
    }

    @After(order = 0)
    public void afterSteps(){
        Config.returnProp();
        WdManager.dismissWD();
    }
}
