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
    @Override
    @EntityGraph(attributePaths = {"carModel", "usedDetails"})
    Optional<CarConfigurationJpaEntity> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = {"carModel", "usedDetails"})
    List<CarConfigurationJpaEntity> findAll();
}