package ru.pogosian.business.users;

import java.util.UUID;

public final class Client extends User{
    Client(String name) {
        super(name);
    }
    public Client(UUID id, String name) {
        super(id, name);
    }
}