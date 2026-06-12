package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCarRepository extends JpaRepository<CarJpaEntity, UUID>, JpaSpecificationExecutor<CarJpaEntity> {
    @Override
    @EntityGraph(attributePaths = {"configuration", "configuration.carModel", "configuration.usedDetails"})
    List<CarJpaEntity> findAll();

    @Override
    @EntityGraph(attributePaths = {"configuration", "configuration.carModel", "configuration.usedDetails"})
    Optional<CarJpaEntity> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = {"configuration", "configuration.carModel", "configuration.usedDetails"})
    List<CarJpaEntity> findAll(Specification<CarJpaEntity> specification);
}