package integration.stepdefs;

import com.eclipsesource.json.JsonObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.TestRunner;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class RegistrationStepDefinitions {

    private Response response;
    private RequestSpecification request;

    @Given("^the following registration data$")
    public void given_the_following_registration_data(DataTable dataTable) {
        RestAssured.baseURI = TestRunner.BASE_URL;
        request = RestAssured.given();

        Map<String, String> map = dataTable.asMap(String.class, String.class);

        request.when().body(withJsonContaining(map.get("name"), map.get("username"), map.get("password"))).contentType("application/json");
    }

    private String withJsonContaining(String name, String username, String password) {
        return new JsonObject()
                .add("name", name)
                .add("username", username)
                .add("password", password)
                .toString();
    }

    @When("^call registration api$")
    public void when_call_registration_api() {
        response = request.when().post("/v1/users");
    }

    @Then("^the status code of response should be (\\d+)$")
    public void then_the_status_code_of_response_should_be(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("^content type should be in JSON format$")
    public void then_content_type_should_be_in_JSON_format() {
        response.then().assertThat().contentType(ContentType.JSON);
    }

    @Then("^response body attribute (.*) should not be null$")
    public void then_response_body_attribute_should_not_be_null(String attribute) {
        response.then().body(attribute, not(emptyOrNullString()));
    }

    @Then("^response body attribute (.*) should be equals (.*)$")
    public void then_response_body_attribute_should_be_equals(String attribute, String value) {
        response.then().body(attribute, equalTo(value));
    }

    @Then("^response body attribute (.*) should be null$")
    public void response_body_attribute_should_be_null(String attribute) {
        response.then().body(attribute, is(emptyOrNullString()));
    }

}