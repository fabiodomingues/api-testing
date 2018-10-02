package br.com.fd.domain.users;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public List<User> all() {
        return unmodifiableList(users);
    }

}