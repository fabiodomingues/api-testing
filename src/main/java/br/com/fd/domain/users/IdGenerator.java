package br.com.fd.domain.users;

import java.util.UUID;

public class IdGenerator {

    public String next() {
        return UUID.randomUUID().toString();
    }

}