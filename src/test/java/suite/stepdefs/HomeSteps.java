package suite.stepdefs;

import cucumber.api.java.en.Then;
import rga.pages.home.HomePage;
import rga.utils.FlowChart;

public class HomeSteps {

    @Then("^user Clicks Sign Out Link$")
    public void iClickSignOutLink() {
        try {
            HomePage.getInstance().singOut();
            FlowChart.addFlowChart(true, "Sign Out", false);
        }catch (Throwable ex){
            FlowChart.featureName = "Sign Out";
            throw ex;
        }
    }
}
