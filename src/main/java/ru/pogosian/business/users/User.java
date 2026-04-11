package ru.pogosian.business.users;

import java.util.UUID;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class User {
    private final UUID id;
    private final String name;

    protected User (@NotNull UUID id, @NotNull String name) {
        if(name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
        this.id = id;
    }

    protected User(@NotNull String name) {
        if (name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
        this.id = UUID.randomUUID();
    }
}