package rga.pages;

import common.wdm.WdManager;
import rga.utils.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BasePage {
    public BasePage() {
        PageFactory.initElements(WdManager.getAjaxEle(), this);
    }

    protected void type(WebElement element, String val) {
        clickByJs(element);
        element.clear();
        element.sendKeys(val);
        Wait.untilJqueryIsDone(90);
    }

    protected void typeByJs(WebElement element, String val) {
        setClearInputValue(element);
        ((JavascriptExecutor) WdManager.get()).executeAsyncScript("arguments[0].value='" + val + "'", element);
    }

    protected void setClearInputValue(WebElement element) {
        ((JavascriptExecutor) WdManager.get()).executeAsyncScript("arguments[0].value=''", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) WdManager.get()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickLinkByText(String text) {
        click(WdManager.get().findElement(By.linkText(text)));
        Wait.untilPageLoadComplete(90);
    }

    public void click(WebElement element) {
        element.click();
        Wait.untilPageLoadComplete(90);
        Wait.untilJqueryIsDone(90);
    }

    public void clickWithOutWait(WebElement element) {
        element.click();
    }

    protected void clickByJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WdManager.get();
        executor.executeScript("arguments[0].click();", element);
    }

    protected void clickByJs(String js, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WdManager.get();
        executor.executeScript(js, element);
        Wait.untilJqueryIsDone(90);
    }

    protected void selectByVisibleText(WebElement element, String value) {
        Select drpCountry = new Select(element);
        drpCountry.selectByVisibleText(value);
    }

    protected void selectByIndex(WebElement element, int index) {
        Select drpCountry = new Select(element);
        drpCountry.selectByIndex(index);
    }

    protected void selectByValue(WebElement element, String value) {
        Select drpCountry = new Select(element);
        drpCountry.selectByValue(value);
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getValue(WebElement element) {
        return element.getAttribute("value");
    }


    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Throwable e) {
            return false;
        }
    }

    protected void selectUlByValue(WebElement dropdown, String searchText){
        List<WebElement> options = dropdown.findElements(By.tagName("li"));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(searchText)) {
                clickByJs(option); // click the desired option
                break;
            }
        }
    }

    protected void typeValueByJs(WebElement element, String value){
        JavascriptExecutor jse = (JavascriptExecutor)WdManager.get();
        jse.executeScript("arguments[0].setAttribute('value', '" + value +"')", element);
    }

    protected void typeAndTab(WebElement element, String val) {
        element.clear();
        element.sendKeys(val);
        element.sendKeys(Keys.TAB);
        Wait.untilJqueryIsDone(90);
    }

    protected void cleanValue(WebElement element){
        do {
            element.click();
            element.clear();
        }while (element.getText().isEmpty() && element.getAttribute("value").isEmpty());

    }

    protected void reTypeAndTab(WebElement element, String val) {
        do {
            element.click();
            element.clear();
            element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
            element.sendKeys(val);
            element.sendKeys(Keys.TAB);
        }while (!element.getText().equalsIgnoreCase(val) || !element.getAttribute("value").equalsIgnoreCase(val));

        Wait.untilJqueryIsDone(90);
    }

}
