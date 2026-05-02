package ru.pogosian;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarDetailRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaCarModelRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaUserRepository;

import java.util.UUID;

public class RepositoryIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void userRepositoryFindsSeedUser(){
        var user = userRepository.findByKeycloakId(UUID.fromString("90000000-0000-0000-0000-000000000001"));
        Assertions.assertEquals("petya", user.getName());
    }
}
