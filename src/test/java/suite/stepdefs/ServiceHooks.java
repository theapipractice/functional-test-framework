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
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import flowstep.business.datacontext.TestContext;
import flowstep.business.entities.DeviceLists;
import flowstep.business.enums.Context;
import flowstep.utils.Common;
import flowstep.utils.FlowChart;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import flowstep.utils.Parser;

public class ServiceHooks {
    @Before(order = 0)
    public void beforeScenario() {
        Config.loadEnvInfoToQueue();
    }

    @Before(order = 1)
    public void beforeSteps(Scenario scenario) throws MalformedURLException {
        String browserName = Parser.asString(TestContext.getScenarioContext().getContext(Context.BROWSER_NAME));
        if (browserName.isEmpty())
            browserName = Config.getProp("browser");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("tz","Asia/Ho_Chi_Minh");
        desiredCapabilities.setCapability("idleTimeout", 300);
        switch (Config.getProp("typeRun")) {
            case "remote":
                switch (browserName) {
                    case "gc":
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
                        desiredCapabilities.setCapability("name", "Scenario : " + scenario.getName() + " is run at " + Common.getCurrentTime());
                        WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
                        break;
                    case "ff":
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
                        desiredCapabilities.setCapability("name", scenario.getName());
                        WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
                        break;
                    case "ie":
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.IE);
                        desiredCapabilities.setCapability("name", scenario.getName());
                        WdManager.set(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
                        break;
                    case "mb":
                        WdManager.set(new IOSDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), DeviceLists.getIosDeviceListByName(Config.getProp("deviceName"))));
                        break;
                }
                break;
            case "direct":
                switch (browserName) {
                    case "gc":
                        if (scenario.getName().contains("Web")) {
                            WDFactory.getConfig().setChromeDriverVersion("80");
                            WdManager.set(WDFactory.initBrowser(browserName));
                        }
                        break;
                    case "ff":
                        if (scenario.getName().contains("Web")) {
                            WdManager.set(WDFactory.initBrowser(browserName));
                        }
                        break;
                    case "sfr":
                        if (scenario.getName().contains("Web"))
                            WdManager.set(WDFactory.initBrowser(browserName));
                        break;
                    case "mb":
                        WdManager.set(new IOSDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), DeviceLists.getIosDeviceListByName(Config.getProp("deviceName"))));
                        break;
                    default:
                        throw new WebDriverException("No browser specified");
                }
                break;
        }

        if (scenario.getName().contains("Web")) {
            WdManager.get().get(Config.getProp("url"));
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        String screenshotName = scenario.getName().replaceAll(" ", "_");
        if (scenario.isFailed()) {
            try {
                FlowChart.flowChartName = screenshotName;
                FlowChart.addFlowChart(false, FlowChart.featureName, true);
                File sourcePath = ((TakesScreenshot) WdManager.get()).getScreenshotAs(OutputType.FILE);
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");

                Files.copy(sourcePath, destinationPath);
                String screenshotsPath = "./screenshots/" + screenshotName + ".png";
                Reporter.addScreenCaptureFromPath(screenshotsPath);

                if (scenario.getName().contains("Web")) {
                    Cookie cookie = new Cookie("zaleniumTestPassed", "false");
                    WdManager.get().manage().addCookie(cookie);
                }
            } catch (IOException e) {
                Log.error(e.getMessage());
                if (scenario.getName().contains("Web")) {
                    Cookie cookie = new Cookie("zaleniumTestPassed", "false");
                    WdManager.get().manage().addCookie(cookie);
                }
            }
        } else {
            FlowChart.flowChartName = screenshotName;
            FlowChart.addFlowChart(true, "", true);

            if (scenario.getName().contains("Web")) {
                Cookie cookie = new Cookie("zaleniumTestPassed", "true");
                WdManager.get().manage().addCookie(cookie);
            }
        }
    }

    @After(order = 0)
    public void afterSteps(Scenario scenario) {
        if (scenario.getName().contains("Web")) {
            //Config.returnProp();
            WdManager.dismissWD();
        }
    }
}
