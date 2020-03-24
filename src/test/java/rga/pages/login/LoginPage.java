package rga.pages.login;

import rga.pages.BasePage;
import rga.pages.common.NotificationMessagePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    private static LoginPage instance;
    public static LoginPage getInstance(){
        return new LoginPage();
    }

    @FindBy(name = "user_name")
    WebElement usernameField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(id = "login")
    WebElement loginFormLocator;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    @FindBy(xpath = "//span[contains(@class,'ant-avatar-image')]")
    WebElement successMessageLocator;

    @FindBy(xpath = "//div[contains(@class,'ant-form-explain')]")
    WebElement failureMessageLocator;

    public void LoginToFlowStep(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
    }

    public void submit() throws InterruptedException {
        click(loginButton);
        NotificationMessagePage.getInstance().isLatestVersion(10);
    }

    public boolean successMessagePresent() {
        return isDisplayed(successMessageLocator);
    }

    public boolean failureMessagePresent() {
        return isDisplayed(failureMessageLocator);
    }

}
