package ru.pogosian.business.users;

import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class User {
    private final UUID id = UUID.randomUUID();
    private final String name;

    protected User(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
    }
}