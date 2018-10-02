package integration.stepdefs;

import br.com.fd.domain.users.RegistrationData;
import com.eclipsesource.json.JsonObject;
import com.google.inject.Inject;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.TestRunner;
import integration.common.ApiTestContext;
import integration.dsl.ApplicationDSL;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class AuthenticationStepDefinitions {

    @Inject
    private ApiTestContext apiTestContext;

    @Given("^user already exists$")
    public void given_i_have_the_following_registration_data(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        ApplicationDSL.register(new RegistrationData(map.get("name"), map.get("username"), map.get("password")));

        apiTestContext.setJsonToRequest(withJsonContaining(map.get("username"), map.get("password")));
    }

    @When("^I call the login api$")
    public void when_call_registration_api() {
        apiTestContext.setResponse(apiTestContext.getRequest().when().post("/v1/login"));
    }

    private String withJsonContaining(String username, String password) {
        return new JsonObject()
                .add("username", username)
                .add("password", password)
                .toString();
    }
}