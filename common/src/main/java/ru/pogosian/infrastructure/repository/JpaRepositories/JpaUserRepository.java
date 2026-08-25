package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByKeycloakId(UUID keycloakId);
    List<UserJpaEntity> findAllByType(UserType type);
}