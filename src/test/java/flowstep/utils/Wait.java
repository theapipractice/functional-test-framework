package flowstep.utils;

import java.util.function.Function;
import common.utils.Log;
import common.wdm.WdManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

    public static void untilJqueryIsDone(int timeoutInSeconds){
        until((d) ->
        {
            Boolean isJqueryCallDone = (Boolean)((JavascriptExecutor) WdManager.get()).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone) Log.info("JQuery call is in Progress");
            return isJqueryCallDone;
        }, timeoutInSeconds);
    }

    public static void untilPageLoadComplete(int timeoutInSeconds){
        until((d) ->
        {
            Boolean isPageLoaded = (Boolean)((JavascriptExecutor) WdManager.get()).executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded) Log.info("Document is loading");
            return isPageLoaded;
        }, timeoutInSeconds);
    }

    private static void until(Function<WebDriver, Boolean> waitCondition, int timeoutInSeconds){
        WebDriverWait webDriverWait = new WebDriverWait(WdManager.get(), timeoutInSeconds);
        try{
            webDriverWait.until(waitCondition);
        }catch (Exception e){
            Log.error(e.getMessage());
        }
    }

    public  static  void waitUntilElementVisible(WebElement ele) {
        WdManager.getWait().until(ExpectedConditions.visibilityOf(ele));
    }

    public static void waitUntilElementNotVisible(WebElement ele) {
        WdManager.getWait().until(ExpectedConditions.invisibilityOf(ele));
    }

    public  static void waitUntilElementDisplay(WebElement element, int timeOut) {
        for (int i = 0; i < timeOut; i++) {
            try {
                if (element.isDisplayed() && element.isEnabled())
                    break;
                Thread.sleep(1000);
            }
            catch (Exception ex) {
                Log.error(ex.getMessage());
            }

        }
    }

    public static boolean waitForJQueryProcessing(int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            new WebDriverWait(WdManager.get(), timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject)
                            .executeScript("return jQuery.active == 0");
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) WdManager.get())
                    .executeScript("return window.jQuery != undefined && jQuery.active === 0");
            return jQcondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }

    public static void waitUntilElementToBeClickable(WebElement ele) {
        WdManager.getWait().until(ExpectedConditions.elementToBeClickable(ele));
    }

}
