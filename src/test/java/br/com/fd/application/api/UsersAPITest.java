package br.com.fd.application.api;

import br.com.fd.domain.users.RegistrationData;
import br.com.fd.domain.users.User;
import br.com.fd.domain.users.UserService;
import br.com.fd.domain.users.UsernameAlreadyInUseException;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsersAPITest {

    private static final String ID = UUID.randomUUID().toString();
    private static final String PASSWORD = "John Doe";
    private static final String NAME = "john.doe";
    private static final String USERNAME = "j04n";

    private static final RegistrationData REGISTRATION_DATA = new RegistrationData(NAME, USERNAME, PASSWORD);

    private static final User USER = new User(ID, NAME, USERNAME, PASSWORD);
    private static final List<User> USERS = asList(USER);

    @Mock private Request request;
    @Mock private Response response;

    @Mock private UserService userService;

    private UsersAPI usersAPI;

    @Before
    public void setUp() throws UsernameAlreadyInUseException {
        usersAPI = new UsersAPI(userService);

        given(request.body()).willReturn(jsonContaining(REGISTRATION_DATA));
        given(userService.createUser(REGISTRATION_DATA)).willReturn(USER);
    }

    @Test
    public void should_create_a_new_user() throws UsernameAlreadyInUseException {
        usersAPI.createUser(request, response);
        
        verify(userService).createUser(REGISTRATION_DATA);
    }

    @Test public void should_return_json_representing_a_newly_created_user() {
        String result = usersAPI.createUser(request, response);

        verify(response).status(201);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(USER));
    }

    @Test
    public void should_return_an_error_when_creating_a_user_with_an_existing_username() throws UsernameAlreadyInUseException {
        given(userService.createUser(REGISTRATION_DATA)).willThrow(UsernameAlreadyInUseException.class);

        String result = usersAPI.createUser(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Username already in use.");
    }

    @Test
    public void should_return_all_users() {
        given(userService.allUsers()).willReturn(USERS);

        String result = usersAPI.allUsers(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(USERS));
    }

    private String jsonContaining(List<User> users) {
        JsonArray json = new JsonArray();
        users.forEach(user -> json.add(jsonObjectFor(user)));
        return json.toString();
    }

    private String jsonContaining(User user) {
        return jsonObjectFor(user).toString();
    }

    private JsonObject jsonObjectFor(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("name", user.getName())
                .add("username", user.getUsername());
    }

    private String jsonContaining(RegistrationData registrationData) {
        return new JsonObject()
                .add("name", registrationData.getName())
                .add("username", registrationData.getUsername())
                .add("password", registrationData.getPassword())
                .toString();
    }
}