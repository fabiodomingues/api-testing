package br.com.fd.application.api;

import br.com.fd.domain.users.RegistrationData;
import br.com.fd.domain.users.User;
import br.com.fd.domain.users.UserService;
import br.com.fd.domain.users.UsernameAlreadyInUseException;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import spark.Request;
import spark.Response;

import java.util.List;

import static br.com.fd.infrastructure.json.UserJson.jsonFor;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class UsersAPI {

    private UserService userService;

    public UsersAPI(UserService userService) {
        this.userService = userService;
    }

    public String createUser(Request request, Response response) {
        RegistrationData registration = registrationDataFrom(request);

        try {
            User user = userService.createUser(registration);

            response.status(CREATED_201);
            response.type("application/json");

            return jsonFor(user);
        } catch (UsernameAlreadyInUseException e) {
            response.status(BAD_REQUEST_400);
            response.type("application/json");

            return new JsonObject()
                    .add("message", "Username already in use.")
                    .toString();
        }
    }

    public String allUsers(Request request, Response response) {
        List<User> users = userService.allUsers();
        response.status(OK_200);
        response.type("application/json");
        return jsonFor(users);
    }

    private RegistrationData registrationDataFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();

        return new RegistrationData(
                json.getString("name", ""),
                json.getString("username", ""),
                json.getString("password", ""));
    }

}