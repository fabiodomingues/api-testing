package br.com.fd.domain.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<User> userFor(UserCredentials userCredentials) {
        return users.stream()
                .filter(userCredentials::matches)
                .findFirst();
    }
}