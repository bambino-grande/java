package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarModelJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCarModelRepository extends JpaRepository<CarModelJpaEntity, UUID> {
    List<CarModelJpaEntity> findAllByRemovedFalse();
    Optional<CarModelJpaEntity> findByIdAndRemovedFalse(UUID id);
    boolean existsByIdAndRemovedFalse(UUID id);
}
