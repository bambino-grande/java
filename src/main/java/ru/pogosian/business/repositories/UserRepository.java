package ru.pogosian.business.repositories;

import ru.pogosian.business.users.User;

import java.util.UUID;
import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
}
