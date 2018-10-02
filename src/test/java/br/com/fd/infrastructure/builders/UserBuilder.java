package br.com.fd.infrastructure.builders;

import br.com.fd.domain.users.User;

import java.util.UUID;

public class UserBuilder {
    private String id = UUID.randomUUID().toString();
    private String name = "Name";
    private String username = "user.name";
    private String password = "password";

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(id, name, username, password);
    }

}