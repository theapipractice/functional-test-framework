package suite.stepdefs;

import common.wdm.WdManager;
//import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.testng.Assert;
import rga.pages.common.MessagePage;
import rga.pages.home.HomePage;
import rga.pages.login.LoginPage;
import rga.utils.FlowChart;
import rga.utils.ZapSecurity;

public class LoginSteps {
    private Response response = null;

    @Given("^user opens the login page$")
    public void iOpenTheLoginPage() {
        FlowChart.addFlowChart(true, "Open Login Page", false);
    }

    @And("^user checks that all fields are displayed correctly$")
    public void iEnsureThatAllFieldsAreDisplayedCorrectly() {
        LoginPage loginPage = LoginPage.getInstance();

        try {
            Assert.assertEquals(loginPage.getHeaderLoginPage(), "Login");
            Assert.assertTrue(loginPage.isEmailFieldDisplayed());

            Assert.assertTrue(loginPage.isPasswordFieldDisplayed());
            Assert.assertTrue(loginPage.isSignInButtonDisplayed());

            FlowChart.addFlowChart(true, "Login Screen Page", false);
        }catch (Throwable ex){
            FlowChart.featureName = "Login Screen Page";
            throw ex;
        }

    }

    @Then("^user login with invalid information$")
    public void iSignInWithInvalidInformationToLoginPage(DataTable table) throws InterruptedException {
        try {
            for (int i = 0; i < table.asLists().size(); i++) {
                String email = table.asLists().get(i).get(0).toString();
                String pass = table.asLists().get(i).get(1).toString();
                LoginPage.getInstance().LoginToRga(email, pass);

                Thread.sleep(1000);
                Assert.assertEquals(MessagePage.getInstance().getMessage(), "Wrong email or password");
            }
        }catch (Throwable ex){
            FlowChart.featureName = "Login as invalid";
            throw ex;
        }

        FlowChart.addFlowChart(true, "Login as invalid", false);
    }

    @Then("^user login with valid information$")
    public void iSignInWithValidInformation(DataTable table) {
        HomePage homePage = HomePage.getInstance();
        try {
            for (int i = 0; i < table.asLists().size(); i++) {
                String userType = table.asLists().get(i).get(0);
                switch (userType){
                    case "claim":
                        String email = table.asLists().get(i).get(1);
                        String pass = table.asLists().get(i).get(2);
                        LoginPage.getInstance().LoginToRga(email, pass);

                        homePage.clickMenuIcon();
                        Assert.assertEquals(homePage.getMenuItemValue().size(), 2);
                        Assert.assertEquals(homePage.getMenuItemValue().get(0), "Claims");
                        Assert.assertEquals(homePage.getMenuItemValue().get(1), "Underwriting");

                        HomePage.getInstance().singOut();
                        break;
                    case "UnderWriting":
                        email = table.asLists().get(i).get(1);
                        pass = table.asLists().get(i).get(2);
                        LoginPage.getInstance().LoginToRga(email, pass);

                        homePage.clickMenuIcon();
                        Assert.assertEquals(homePage.getMenuItemValue().size(), 1);
                        Assert.assertEquals(homePage.getMenuItemValue().get(0), "Claims");
                        break;
                }
            }
        }catch (Throwable ex){
            FlowChart.featureName = "Login as valid";
            throw ex;
        }
        FlowChart.addFlowChart(true, "Login as valid", false);
    }

    @Then("^user actives scanning attempts to find potential vulnerabilities before login$")
    public void userActivesScanningAttemptsToFindPotentialVulnerabilitiesBeforeLogin() {
        String target = WdManager.get().getCurrentUrl();
        ZapSecurity zap = new ZapSecurity(target);
        zap.waitForPassiveScanToComplete();
        zap.startSpiderScan();
        zap.startActiveScan();
    }

    @Then("^user actives scanning attempts to find potential vulnerabilities after login$")
    public void userActivesScanningAttemptsToFindPotentialVulnerabilitiesAfterLogin() {
        String target = WdManager.get().getCurrentUrl() + "/claims";
        ZapSecurity zap = new ZapSecurity(target);
        zap.waitForPassiveScanToComplete();
        zap.startSpiderScan();
        zap.startActiveScan();
        zap.getReports();
    }
}
