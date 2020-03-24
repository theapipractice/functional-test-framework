package rga.pages.common;

import rga.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NotificationMessagePage extends BasePage {
    private static NotificationMessagePage instance;
    public static NotificationMessagePage getInstance(){
       return new NotificationMessagePage();
    }

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ant-notification-topRight')]")
    WebElement errorMessage;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ant-notification-notice-description')]")
    WebElement messageNotification;

	@FindBy(xpath = ".//div[@class='ant-notification-notice-message']")
    WebElement notificationMessage;

	@FindBy(xpath = "//button[span[contains(.,'Reload')]]")
    WebElement reloadButton;

    public boolean isMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getMessage() {
        return getText(errorMessage);
    }

    public String getMessageNotification() {
        return getText(messageNotification);
    }

    public boolean isSystemMessageDisplayed() {
        return isDisplayed(messageNotification);
    }

    public void waitNotificationMessageDisplay(int timeout){
        for (int i = 0; i < timeout; i++) {
            try {
                if (notificationMessage.isDisplayed())
                    break;
                if (i == 10)
                    break;
                Thread.sleep(500);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean isNotificationMessageDisplayed() {
        return isDisplayed(notificationMessage);
    }

    public void clickReloadButton(){
        click(reloadButton);
    }

    public void isLatestVersion(int timeout) throws InterruptedException {
        for (int i = 0; i < timeout; i++) {
            if (isDisplayed(reloadButton)) {
                clickReloadButton();
                break;
            }
            Thread.sleep(1000);
        }
    }
}
