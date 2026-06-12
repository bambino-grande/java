package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaComplectationCarOrderRepository extends JpaRepository<ComplectationCarOrderJpaEntity, UUID> {
    Page<ComplectationCarOrderJpaEntity> findAllByClientId(UUID clientId, Pageable pageable);
}
