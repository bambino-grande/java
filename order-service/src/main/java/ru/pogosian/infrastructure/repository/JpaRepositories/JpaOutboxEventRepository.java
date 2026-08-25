package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.infrastructure.repository.JpaEntity.OutboxEventJpaEntity;

import java.util.List;
import java.util.UUID;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEventJpaEntity, UUID> {
    List<OutboxEventJpaEntity> findByOutboxStatus(OutboxStatus status);
}
