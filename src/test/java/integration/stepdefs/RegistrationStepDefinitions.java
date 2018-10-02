package integration.stepdefs;

import br.com.fd.domain.users.RegistrationData;
import com.eclipsesource.json.JsonObject;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.TestRunner;
import integration.common.ApiTestContext;
import integration.dsl.ApplicationDSL;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static integration.dsl.ApplicationDSL.register;
import static org.hamcrest.Matchers.*;

public class RegistrationStepDefinitions {

    @Inject
    private ApiTestContext apiTestContext;

    @Given("^I have the following registration data$")
    public void given_i_have_the_following_registration_data(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);

        apiTestContext.setJsonToRequest(withJsonContaining(map.get("name"), map.get("username"), map.get("password")));
    }

    @Given("^already exists a user with username \"(.*)\"$")
    public void already_exists_a_user_with_username(String username) {
        register(new RegistrationData("Name", username, "password"));
    }

    @When("^I call the registration api$")
    public void when_call_registration_api() {
        apiTestContext.setResponse(apiTestContext.getRequest().when().post("/v1/users"));
    }

    private String withJsonContaining(String name, String username, String password) {
        return new JsonObject()
                .add("name", name)
                .add("username", username)
                .add("password", password)
                .toString();
    }
}