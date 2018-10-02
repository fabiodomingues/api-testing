package integration.dsl;

import br.com.fd.domain.users.RegistrationData;
import br.com.fd.domain.users.User;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import integration.TestRunner;
import io.restassured.response.Response;

import static integration.common.ApiTestContext.BASE_URL;
import static io.restassured.RestAssured.given;

public class ApplicationDSL {

    public static User register(RegistrationData registrationData) {
        Response response = given()
                .body(withRegistrationJsonFor(registrationData))
                .when()
                .post(BASE_URL + "/v1/users");

        String userId = userIdFrom(response);

        return userFrom(registrationData, userId);
    }

    private static String userIdFrom(Response response) {
        JsonObject responseJson = Json.parse(response.body().asString()).asObject();
        return responseJson.getString("id", "");
    }

    private static User userFrom(Response response) {
        JsonObject responseJson = Json.parse(response.body().asString()).asObject();

        return new User(
                responseJson.getString("id", ""),
                responseJson.getString("name", ""),
                responseJson.getString("username", ""),
                responseJson.getString("password", "")
        );
    }

    private static String withRegistrationJsonFor(RegistrationData registrationData) {
        return new JsonObject()
                .add("name", registrationData.getName())
                .add("username", registrationData.getUsername())
                .add("password", registrationData.getPassword())
                .toString();
    }

    private static User userFrom(RegistrationData registrationData, String userId) {
        return new User(userId, registrationData.getName(), registrationData.getUsername(), registrationData.getPassword());
    }
}
