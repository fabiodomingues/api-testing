package integration.stepdefs;

import com.google.inject.Inject;
import cucumber.api.java.en.Then;
import integration.common.ApiTestContext;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class CommonStepDefinitions {

    @Inject
    private ApiTestContext apiTestContext;

    @Then("^the status code of response should be (\\d+)$")
    public void then_the_status_code_of_response_should_be(Integer statusCode) {
        apiTestContext.getResponse().then().statusCode(statusCode);
    }

    @Then("^content type should be in JSON format$")
    public void then_content_type_should_be_in_JSON_format() {
        apiTestContext.getResponse().then().assertThat().contentType(ContentType.JSON);
    }

    @Then("^response body attribute (.*) should not be null$")
    public void then_response_body_attribute_should_not_be_null(String attribute) {
        apiTestContext.getResponse().then().body(attribute, not(isEmptyOrNullString()));
    }

    @Then("^response body attribute (.*) should be equals \"(.*)\"$")
    public void then_response_body_attribute_should_be_equals(String attribute, String value) {
        apiTestContext.getResponse().then().body(attribute, equalTo(value));
    }

    @Then("^response body attribute (.*) should be null$")
    public void response_body_attribute_should_be_null(String attribute) {
        apiTestContext.getResponse().then().body(attribute, isEmptyOrNullString());
    }
}
