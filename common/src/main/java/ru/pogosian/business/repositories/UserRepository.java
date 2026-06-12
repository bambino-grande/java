package ru.pogosian.business.repositories;

import ru.pogosian.business.users.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    User findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
    User findByKeycloakId(UUID keycloakId);
    List<User> findAllManagers();
}
