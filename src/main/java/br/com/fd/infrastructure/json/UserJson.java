package br.com.fd.infrastructure.json;

import br.com.fd.domain.users.User;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.List;

public class UserJson {

    public static String jsonFor(User user) {
        return jsonObjectFor(user)
                .toString();
    }

    public static String jsonFor(List<User> users) {
        JsonArray jsonArray = new JsonArray();
        users.forEach(user -> jsonArray.add(jsonObjectFor(user)));
        return jsonArray.toString();
    }

    private static JsonObject jsonObjectFor(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("name", user.getName())
                .add("username", user.getUsername());
    }

}