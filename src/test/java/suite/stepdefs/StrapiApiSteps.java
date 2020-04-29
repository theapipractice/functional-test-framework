package suite.stepdefs;

//import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import flowstep.utils.JsonDataReader;
import flowstep.utils.Parser;
import flowstep.utils.RestUtil;
import java.util.HashMap;
import java.util.Map;


public class StrapiApiSteps {
    private Response response = null;
    String token = "";
    String id= "";
    String org = "";

    @Given("^user set authorization endpoint$")
    public void iSubmitAuthorization() {
        RestUtil.setBaseURI("http://localhost:1337/auth/local");
        RestUtil.setContentType("application/x-www-form-urlencoded");
    }

    @Then("^user send post auth request$")
    public void iSendPostAuthRequest() {
        Map<String, String> formParam = new HashMap<>();
        formParam.put("identifier", "apiuser1");
        formParam.put("password", "Admin123!");

        RestUtil.setFormParam(formParam);
        response = RestUtil.post();
        token = RestUtil.getJsonPath(response).getString("jwt");
    }

    @Given("^user access to the hospital endpoint$")
    public void iAccessToTheStrapiEndpoint() {
        RestUtil.setBaseURI("http://localhost:1337/hospitals/");
        RestUtil.setContentType("application/x-www-form-urlencoded");

        RestUtil.requestSpec = RestAssured.given();
        RestUtil.requestSpec.header("Authorization", "Bearer " + token);
    }

    @Then("^user sends get hospital request with id$")
    public void iSendGetRequest(DataTable table) {
        id = table.asLists().get(0).get(0);
        RestUtil.path = "/" + id;
        response = RestUtil.get();
    }

    @And("^user checks the get response body and code$")
    public void iCheckTheResponse(DataTable table) {
        Assert.assertEquals(response.getStatusCode(), Parser.asInt(table.asLists().get(0).get(0)));
        Assert.assertEquals(Parser.asJson(response.getBody().asString()), JsonDataReader.getListHospitalById(id).get(0));
    }

    @Then("^user sends post hospital request with json body$")
    public void iSendPostStrapiRequest() {
        RestUtil.setBody(JsonDataReader.getPost(0).toJSONString());
        response =  RestUtil.post();
    }

    @And("^user checks the response code$")
    public void iCheckThePostResponse(DataTable table) {
        Assert.assertEquals(response.getStatusCode(), Parser.asInt(table.asLists().get(0).get(0)));
    }

    @Given("^user access to the hospital endpoint without authorization$")
    public void iAccessToTheStrapiEndpointWithoutAuthorization() {
        RestUtil.setBaseURI("http://localhost:1337/hospitals/");
        RestUtil.setContentType("application/x-www-form-urlencoded");
    }

    @And("^user checks status invalid response code$")
    public void iCheckStatusCodeResponse(DataTable table) {
        Assert.assertEquals(Parser.asInt(RestUtil.getJsonPath(response).get("statusCode")), Parser.asInt(table.asLists().get(0).get(0)));
        Assert.assertEquals(RestUtil.getJsonPath(response).get("error"), table.asLists().get(0).get(1));
        Assert.assertEquals(RestUtil.getJsonPath(response).get("message"), table.asLists().get(0).get(2));
    }

    @Then("^user sends get hospital request with limit : \"([^\"]*)\" and Organization : \"([^\"]*)\"$")
    public void userSendsGetHospitalRequestWithAnd(String limit, String organization) throws Throwable {
        Map<String, String> formParam = new HashMap<>();
        formParam.put("_limit", limit);
        formParam.put("organization", organization);
        org = organization;

        RestUtil.setFormParam(formParam);
        response = RestUtil.get();
    }

    @And("^user checks the get response body and code \"([^\"]*)\"$")
    public void userChecksTheGetResponseBodyAndCode(String code) throws Throwable {
        Assert.assertEquals(response.getStatusCode(), Parser.asInt(code));
        Assert.assertEquals(Parser.asListJson(response.getBody().asString()), JsonDataReader.getListHospitalByOrganization(org));
    }
}
