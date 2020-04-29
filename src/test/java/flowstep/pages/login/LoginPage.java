package flowstep.pages.login;

import flowstep.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    public static LoginPage getInstance(){
        return new LoginPage();
    }


    @FindBy(xpath = "//div[@class='v-card__title']")
    WebElement lbHeaderLogin;

    @FindBy(xpath = "//*[@id=\"input-12\"]")
    WebElement emailField;

    @FindBy(xpath = "//*[@id=\"input-15\"]")
    WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement signInButton;

    public void LoginToRga(String email, String password) {
        deleteAndType(emailField, email);
        deleteAndType(passwordField, password);
        click(signInButton);
    }

    public String getHeaderLoginPage(){
        return getText(lbHeaderLogin);
    }

    public boolean isEmailFieldDisplayed(){
        return isDisplayed(emailField);
    }

    public boolean isPasswordFieldDisplayed(){
        return isDisplayed(passwordField);
    }

    public boolean isSignInButtonDisplayed(){
        return isDisplayed(signInButton);
    }

}
