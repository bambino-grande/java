package ru.pogosian.business.users;

import java.util.UUID;

public class Manager extends User{
    Manager(String name) {
        super(name);
    }
    public Manager(UUID id, UUID keycloakId, String name) {
        super(id, name, keycloakId);
    }
}
