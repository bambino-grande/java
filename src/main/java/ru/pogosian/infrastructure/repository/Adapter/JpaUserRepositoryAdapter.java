package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.UserRepository;
import ru.pogosian.business.users.User;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;
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
        if(!JpaUserRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("User with id " + id + " does not exist");
        return mapper.toDomain(JpaUserRepository.findByIdAndRemovedFalse(id).orElseThrow());
    }

    @Override
    public List<User> findAll() {
        return JpaUserRepository.findAllByRemovedFalse().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        if(!JpaUserRepository.existsByIdAndRemovedFalse(id))
            throw new DomainValidationException("User with id " + id + " does not exist");
        UserJpaEntity UserJpaEntity = JpaUserRepository.findByIdAndRemovedFalse(id).orElseThrow();
        UserJpaEntity.setRemoved(true);
        JpaUserRepository.save(UserJpaEntity);
    }
}
