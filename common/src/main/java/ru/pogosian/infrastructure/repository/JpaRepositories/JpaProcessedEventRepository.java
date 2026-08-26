package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.ProcessedEventJpaEntity;

import java.util.UUID;

public interface JpaProcessedEventRepository extends JpaRepository<ProcessedEventJpaEntity, UUID> {
}
