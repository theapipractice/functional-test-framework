package suite.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import rga.pages.home.UsersPage;
import rga.utils.FlowChart;

import java.util.List;

public class UsersSteps {
    @Given("^I open the users page$")
    public void iOpenTheUsersPage() {
        FlowChart.addFlowChart(true, "Open Page", false);
    }

    @When("^I enter the name \"([^\"]*)\"$")
    public void iEnterTheName(String name) throws Throwable {
        UsersPage.getInstance().enterSearch(name);
    }

    @Then("^I Click the Search Button$")
    public void iClickTheSearchButton() {
        UsersPage.getInstance().clickSearchButton();
    }

    @And("^The user should be returned$")
    public void theUserShouldBeReturned() {
       List<List<String>> list =  UsersPage.getInstance().verifySearchResult();
        Assert.assertEquals(list.get(0).get(0),"John Doe");
        Assert.assertEquals(list.get(0).get(1),"user1@google.com");
        Assert.assertEquals(list.get(0).get(2),"Admin");
        Assert.assertEquals(list.get(0).get(3),"Active");
        Assert.assertEquals(list.get(0).get(4),"View | Edit | Delete");
        FlowChart.addFlowChart(true, "Search User", false);
    }

    @When("^I click Add User Button$")
    public void iClickAddUserButton() {
        UsersPage.getInstance().clickAddUserButton();
    }

    @And("^I verify the user added$")
    public void iVerifyTheUserAdded() {
        try {
            Assert.assertEquals("John Doe", "firstName");
        }catch (Throwable ex){
            FlowChart.featureName = "User is Added";
            throw ex;
        }
    }

    @Then("^I add new user$")
    public void iAddNewUser() {
        try {
            UsersPage.getInstance().enterAddUserForm();
            FlowChart.addFlowChart(true, "Fill Value In Form", false);
        }catch (Throwable ex){
            FlowChart.featureName = "Fill Value In Form";
            throw ex;
        }
    }
}
