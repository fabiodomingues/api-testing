package integration.common;

import cucumber.runtime.java.guice.ScenarioScoped;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

@ScenarioScoped
public class ApiTestContext {

    public static final String BASE_URL = "http://localhost:8080";

    private final RequestSpecification requestSpecification;
    private RequestSpecification request;
    private Response response;

    public ApiTestContext() {
        RequestSpecBuilder build = new RequestSpecBuilder();
        build.setBaseUri(BASE_URL);

        requestSpecification = build.build();
    }

    public RequestSpecification getRequest() {
        if (request == null) {
            request = given().spec(requestSpecification);
        }

        return request;
    }

    public void setJsonToRequest(String json) {
        getRequest().when().body(json).contentType("application/json");
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}