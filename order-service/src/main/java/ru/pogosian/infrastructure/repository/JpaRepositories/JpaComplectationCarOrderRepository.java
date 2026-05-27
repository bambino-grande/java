package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderJpaEntity;

import java.util.UUID;

public interface JpaComplectationCarOrderRepository extends JpaRepository<ComplectationCarOrderJpaEntity, UUID> {
    Page<ComplectationCarOrderJpaEntity> findAllByClientId(UUID clientId, Pageable pageable);
}
