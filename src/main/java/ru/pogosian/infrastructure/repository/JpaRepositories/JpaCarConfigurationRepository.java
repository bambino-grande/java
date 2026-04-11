package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaCarConfigurationRepository extends JpaRepository<CarConfigurationJpaEntity, UUID>{
    @EntityGraph(attributePaths = {"carModel", "usedDetails"})
    Optional<CarConfigurationJpaEntity> findByIdAndRemovedFalse(UUID id);

    boolean existsByIdAndRemovedFalse(UUID id);

    @EntityGraph(attributePaths = {"carModel", "usedDetails"})
    List<CarConfigurationJpaEntity> findAllByRemovedFalse();
}