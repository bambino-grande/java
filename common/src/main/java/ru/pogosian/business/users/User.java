package ru.pogosian.business.users;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public abstract class User {
    private final UUID id;
    private final String name;
    private final UUID keycloakId;

    protected User (@NotNull UUID id, @NotNull String name, UUID keycloakId) {
        if(name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
        this.id = id;
        this.keycloakId = keycloakId;
    }

    protected User(@NotNull String name) {
        if (name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
        this.id = UUID.randomUUID();
        this.keycloakId = null;
    }
}