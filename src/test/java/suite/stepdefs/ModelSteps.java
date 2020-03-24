package suite.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import rga.business.enums.Context;
import rga.pages.common.MenuPage;
import rga.pages.common.NotificationMessagePage;
import rga.pages.home.ModelsPage;
import rga.utils.Parser;
import org.testng.Assert;

import static rga.business.datacontext.TestContext.*;

public class ModelSteps {

    @Then("^I Click on Model Link$")
    public void iClickOnModelLink() {
        MenuPage.getInstance().clickModelsLink();
    }

    @And("^Verify the error message \"([^\"]*)\" will be displayed$")
    public void verifyTheErrorMessageWillBeDisplayed(String msg) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String modelName = Parser.asString(getScenarioContext().getContext(Context.MODEL_NAME));
        msg = String.format("A model with name %s already exists", modelName);
        Assert.assertEquals(NotificationMessagePage.getInstance().getMessageNotification(), msg);
    }

    @Then("^Add New Model With Existing Name \"([^\"]*)\" and Type Of Model \"([^\"]*)\"$")
    public void addNewModelWithExistingNameAndTypeOfModel(String modelName, String type) throws Throwable {
        ModelsPage.getInstance().addNewModel(modelName, type);
        getScenarioContext().setContext(Context.MODEL_NAME, modelName);
    }
}
