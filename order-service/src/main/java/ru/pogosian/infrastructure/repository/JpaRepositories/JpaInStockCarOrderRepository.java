package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;

import java.util.UUID;

public interface JpaInStockCarOrderRepository extends JpaRepository<InStockCarOrderJpaEntity, UUID> {
    Page<InStockCarOrderJpaEntity> findAllByClientId(UUID clientId, Pageable pageable);
}