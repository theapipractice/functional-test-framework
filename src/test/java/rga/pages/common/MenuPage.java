package rga.pages.common;

import common.utils.Log;
import common.wdm.WdManager;
import rga.pages.BasePage;
import rga.utils.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BasePage {
    public static MenuPage getInstance(){
       return  new MenuPage();
    }

    @FindBy(xpath = ".//a[@href='/userflows']")
    WebElement userFlowLink;

    @FindBy(xpath = "//li[@role='menuitem']//a[contains(@href,'/model')]")
    WebElement modelLink;

    @FindBy(xpath = "//a[contains(@href,'/properties')]")
    WebElement propertiesLink;

    private WebElement getLinkByValue(String linkName) {
        return WdManager.get().findElement(By.xpath(".//li[span[.='"+linkName+"']]"));
    }

    public void ClickUserFlowsLink() {
        click(userFlowLink);
    }

    public void clickModelsLink() {
        Wait.waitUntilElementDisplay(modelLink, 90);
        clickByJs(modelLink);
    }

    public void clickPropertiesLink() {
        Wait.waitUntilElementDisplay(propertiesLink, 90);
        clickByJs(propertiesLink);
    }

    public void goToProjectByValue(String value) {
        //Click first level
        try {
            Thread.sleep(3000);
            click(getLinkByValue("Core Platforms"));
            Thread.sleep(3000);
            click(getLinkByValue("Automation Framework"));
        }catch (Exception ex){
            Log.error(ex.getMessage());
        }

    }

    public boolean isUserFlowDisplay() {
        return isDisplayed(userFlowLink);
    }
}
