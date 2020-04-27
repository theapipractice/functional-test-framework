package rga.pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import rga.pages.BasePage;

public class MessagePage extends BasePage {
    public static MessagePage getInstance(){
        return new MessagePage();
    }

    @FindBy(xpath = "//p[@class='red--text']")
    WebElement lbErrorMessage;

    public String getMessage(){
        return getText(lbErrorMessage);
    }
}
