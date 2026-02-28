package ru.pogosian.infrastructure.repository;

import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.User;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private Map<UUID, User> store =  new HashMap<UUID, User>();
    @Override
    public void save(User User) {
        store.put(User.getId(), User);
    }

    @Override
    public User findById(UUID id) {
        User User = store.get(id);
        return User;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}
