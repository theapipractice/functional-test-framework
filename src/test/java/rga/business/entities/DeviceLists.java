package rga.business.entities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DeviceLists {

    public static DesiredCapabilities getIosDeviceListByName(String deviceName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "12.4");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("browserName", "Safari");
        switch(deviceName) {
            case "6":
                capabilities.setCapability("deviceName", "iPhone 6");
                break;
            case "7":
                capabilities.setCapability("deviceName", "iPhone 7");
                break;
            case "8":
                capabilities.setCapability("deviceName", "iPhone 8");
                break;
            case "9":
                capabilities.setCapability("deviceName", "iPhone 9");
                break;
            case "10":
                capabilities.setCapability("deviceName", "iPhone X");
                break;

        }
        return capabilities;
    }

    public static DesiredCapabilities getAndroidDeviceListByName(String deviceName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("browserName", "ANDROID");
        switch(deviceName) {
            case "6":
                capabilities.setCapability("deviceName", "iPhone 6");
                break;
            case "7":
                capabilities.setCapability("deviceName", "samsung_galaxy_s6_7.1.1");
                break;
            case "8":
                capabilities.setCapability("deviceName", "iPhone 8");
                break;
            case "9":
                capabilities.setCapability("deviceName", "iPhone 9");
                break;
            case "10":
                capabilities.setCapability("deviceName", "iPhone X");
                break;

        }
        return capabilities;
    }
}
