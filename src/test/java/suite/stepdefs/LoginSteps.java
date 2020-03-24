package suite.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import rga.pages.login.LoginPage;
import org.testng.Assert;

public class LoginSteps {

    @Given("^I have entered userName \"([^\"]*)\" and passWord \"([^\"]*)\"$")
    public void i_have_entered_userName_and_passWord(String userName, String passWord) throws Throwable {
        LoginPage.getInstance().LoginToFlowStep(userName, passWord);
    }

    @When("^I login$")
    public void i_login() throws Throwable {
        LoginPage.getInstance().submit();
    }

    @Then("^I should be informed that login \"([^\"]*)\"$")
    public void i_should_be_informed_that_login(String result) throws Throwable {
        switch (result) {
            case "passed":
                boolean isPass = LoginPage.getInstance().successMessagePresent();
                Assert.assertTrue(isPass, "Login Unsuccessfully");
                break;
            case "failed":
                boolean isFail = LoginPage.getInstance().failureMessagePresent();
                Assert.assertTrue(isFail, "Error Message is not displayed");
                break;
        }
    }
}
