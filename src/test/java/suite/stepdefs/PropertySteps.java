package suite.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import rga.business.enums.Context;
import rga.pages.common.NotificationMessagePage;
import rga.pages.home.PropertiesPage;
import rga.pages.common.MenuPage;
import rga.utils.FlowChart;
import org.testng.Assert;
import static rga.business.datacontext.TestContext.*;

public class PropertySteps {
    @Then("^I Click on Properties Link$")
    public void i_Click_on_Properties_Link() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        MenuPage.getInstance().clickPropertiesLink();
    }

    @Then("^Add new Property$")
    public void add_new_Property() {
        String propertyName = PropertiesPage.getInstance().addNewProperty();
        getScenarioContext().setContext(Context.PROPERTY_NAME, propertyName);
    }

    @Then("^Add new Property with property name \"([^\"]*)\"$")
    public void add_new_Property_with_property_name(String propertyName) throws Throwable {
        try {
            PropertiesPage.getInstance().addNewProperty(propertyName);
            FlowChart.addFlowChart(true, "Throw Exception if Add Invalid Property", false);
        }catch (Throwable ex){
            FlowChart.addFlowChart(false, "Throw Exception if Add Invalid Property", false);
        }
    }

    @Then("^Edit the information Of Property \"([^\"]*)\"$")
    public void edit_the_information_Of_Property(String type) throws Throwable {
        try{
            PropertiesPage.getInstance().editProperty(type);
            FlowChart.addFlowChart(true, "Change Property Type", false);
        }catch (Throwable ex){
            FlowChart.addFlowChart(false, "Change Property Type", false);
        }
    }

    @And("^Verify the error message \"([^\"]*)\" with \"([^\"]*)\"$")
    public void verifyThereWillBeAErrorMessageWith(String message, String type) throws Throwable {
        String msg;
        switch (type){
            case "empty":
                msg = PropertiesPage.getInstance().getErrorOfPropertyField();
                Assert.assertEquals(message, msg);

                String msgSpan = PropertiesPage.getInstance().getErrorOfPropertySpanField();
                Assert.assertEquals("error", msgSpan);
                break;
            case "duplicate":
                msg = NotificationMessagePage.getInstance().getMessageNotification();
                Assert.assertEquals(message, msg);
                break;
        }
    }

}
