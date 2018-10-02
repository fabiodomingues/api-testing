package integration.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class AuthenticationStepDefinitions {

    @Given("the following authentication data")
    public void given_the_following_authentication_data(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new PendingException();
    }

    @When("call authentication api")
    public void when_call_authentication_api() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("response body attribute token should not be null")
    public void then_response_body_attribute_token_should_not_be_null() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}