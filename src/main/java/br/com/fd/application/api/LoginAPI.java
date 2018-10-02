package br.com.fd.application.api;

import br.com.fd.domain.users.User;
import br.com.fd.domain.users.UserCredentials;
import br.com.fd.domain.users.UserRepository;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static br.com.fd.infrastructure.json.UserJson.jsonFor;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class LoginAPI {

    private UserRepository userRepository;

    public LoginAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(Request request, Response response) {
        UserCredentials credentials = credentialsFrom(request);

        Optional<User> user = userRepository.userFor(credentials);

        return user.isPresent()
                ? prepareOKResponse(response, user)
                : prepareErrorResponse(response);
    }

    private String prepareErrorResponse(Response response) {
        response.status(NOT_FOUND_404);
        response.type("application/json");

        return new JsonObject()
                .add("message", "Invalid credentials.")
                .toString();
    }

    private String prepareOKResponse(Response response, Optional<User> user) {
        response.status(OK_200);
        response.type("application/json");

        return jsonFor(user.get());
    }

    private UserCredentials credentialsFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();

        return new UserCredentials(
                json.getString("username", ""),
                json.getString("password", ""));
    }

}