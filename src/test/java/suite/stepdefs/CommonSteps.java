package suite.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import rga.business.enums.Context;
import rga.pages.home.SearchPage;
import rga.pages.login.LoginPage;
import rga.utils.FlowChart;
import rga.utils.Parser;
import org.testng.Assert;

import static rga.business.datacontext.TestContext.getScenarioContext;

public class CommonSteps {
    @Then("^Enter search value in text field \"([^\"]*)\"$")
    public void enter_search_value_in_text_field(String value) throws Throwable {
        String searchValue = Parser.asString(getScenarioContext().getContext(Context.PROPERTY_NAME));
        if (searchValue.isEmpty())
            searchValue = value;

        SearchPage.getInstance().enterSearchValue(searchValue);

        if (SearchPage.getInstance().pageListNumber() <= 1)
            FlowChart.addFlowChart(true, "Search Page", false);
        else
            FlowChart.addFlowChart(false, "Search Page", false);
    }

    @Then("^Verify result \"([^\"]*)\"$")
    public void verify_result(String value) throws Throwable {
        String searchValue = Parser.asString(getScenarioContext().getContext(Context.PROPERTY_NAME));
        if (searchValue.isEmpty())
            searchValue = value;

        Assert.assertEquals(SearchPage.getInstance().getSearchReturn(), searchValue);
    }

    @Then("^Click X button to clear the value$")
    public void click_X_button_to_clear_the_value() throws Throwable {
        SearchPage.getInstance().clickClearSearchButton();
    }

    @Then("^Verify List Number Of first page$")
    public void verify_first_page_of_model() throws Throwable {
        Assert.assertEquals(SearchPage.getInstance().pageListNumber(), 13);
    }

    @Given("^I login successfully with userName \"([^\"]*)\" and passWord \"([^\"]*)\" that login \"([^\"]*)\"$")
    public void iLoginSuccessfullyWithUserNameAndPassWordThatLogin(String userName, String passWord, String result) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        LoginPage.getInstance().LoginToFlowStep(userName, passWord);
        LoginPage.getInstance().submit();
        switch (result) {
            case "passed":
                boolean isPass = LoginPage.getInstance().successMessagePresent();
                if (isPass)
                    FlowChart.addFlowChart(true, "Login", false);
                else
                    FlowChart.addFlowChart(false, "Login", false);
                Assert.assertTrue(isPass, "Login Unsuccessfully");
                break;
            case "failed":
                boolean isFail = LoginPage.getInstance().failureMessagePresent();
                if (isFail)
                    FlowChart.addFlowChart(true, "Login", false);
                else
                    FlowChart.addFlowChart(false, "Login", false);
                Assert.assertTrue(isFail, "Error Message is not displayed");
                break;
        }
    }
}
