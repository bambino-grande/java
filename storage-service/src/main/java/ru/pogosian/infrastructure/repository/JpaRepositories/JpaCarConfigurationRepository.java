package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;

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