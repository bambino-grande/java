package ru.pogosian.business.users;

import java.util.UUID;

public class SystemAdmin extends User{
    SystemAdmin(String name) {
        super(name);
    }
    public SystemAdmin(UUID id, String name) {
        super(id, name);
    }
}
