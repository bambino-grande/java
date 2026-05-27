package ru.pogosian.business.users;

import java.util.UUID;

public class WarehouseAdmin extends User{
    WarehouseAdmin(String name) {
        super(name);
    }

    public WarehouseAdmin(UUID id, String name, UUID keycloakId) {
        super(id, name, keycloakId);
    }
}
