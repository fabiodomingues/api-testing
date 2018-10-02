package br.com.fd.application;

import br.com.fd.application.api.UsersAPI;
import br.com.fd.domain.users.IdGenerator;
import br.com.fd.domain.users.UserRepository;
import br.com.fd.domain.users.UserService;

import static spark.Spark.get;
import static spark.Spark.post;

public class Routes {

    private UsersAPI usersAPI;

    public void create() {
        createAPIs();
        applicationRoutes();
    }

    private void createAPIs() {
        IdGenerator idGenerator = new IdGenerator();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(idGenerator, userRepository);

        usersAPI = new UsersAPI(userService);
    }

    private void applicationRoutes() {
        get("v1/status", (req, res) -> "UP");
        post("v1/users", (req, res) -> usersAPI.createUser(req, res));
        get("v1/users", (req, res) -> usersAPI.allUsers(req, res));
    }

}