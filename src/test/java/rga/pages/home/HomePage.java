package rga.pages.home;

import common.config.Config;
import common.wdm.WdManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import rga.pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    public static HomePage getInstance(){
        return new HomePage();
    }

    @FindBy(xpath = "//div[@class='v-list-item__title']")
    List<WebElement> menuLinks;

    @FindBy(xpath = "//button[img[contains(@class,'h-8 w-8 rounded-full')]]")
    WebElement profileButton;

    @FindBy(xpath = "//div[@class='py-1 rounded-md bg-white shadow-xs']//a[text()='Sign out']")
    WebElement signOutButton;

    @FindBy(xpath = "//i[@class='v-icon notranslate mdi mdi-menu theme--light']")
    WebElement menuIcon;


    public List<String> getMenuItemValue(){
        List<String> list = new ArrayList<>();
        for (WebElement elm: menuLinks) {
            list.add(getText(elm));
        }
        return list;
    }

    public void singOut(){
//        click(profileButton);
//        click(signOutButton);
        WdManager.get().get(Config.getProp("logout"));
    }

    public void clickMenuIcon(){
        click(menuIcon);
    }
}
