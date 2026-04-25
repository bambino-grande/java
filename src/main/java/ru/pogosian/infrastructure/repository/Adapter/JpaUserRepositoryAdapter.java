package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.User;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserType;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaUserRepository;
import ru.pogosian.infrastructure.repository.Mapper.Mapper;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {
    private JpaUserRepository JpaUserRepository;
    private Mapper mapper;

    @Override
    public void save(User User) {
        if(User ==  null)
            throw new DomainValidationException("user is null");
        JpaUserRepository.save(mapper.toJpaEntity(User));
    }

    @Override
    public User findById(UUID id) {
        return mapper.toDomain(JpaUserRepository.findById(id).orElseThrow(() -> new DomainValidationException("User with id " + id + " does not exist")));
    }

    @Override
    public List<User> findAll() {
        return JpaUserRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        UserJpaEntity UserJpaEntity = JpaUserRepository.findById(id).orElseThrow(() -> new DomainValidationException("User with id " + id + " does not exist"));
        UserJpaEntity.setRemoved(true);
        JpaUserRepository.save(UserJpaEntity);
    }

    @Override
    public User findByKeycloakId(UUID keycloakId) {
        return mapper.toDomain(JpaUserRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new DomainValidationException("User with keycloak " + keycloakId + " does not exist")));
    }

    @Override
    public List<User> findAllManagers() {
        return JpaUserRepository.findAllByType(UserType.MANAGER).stream().map(mapper::toDomain).toList();
    }
}
