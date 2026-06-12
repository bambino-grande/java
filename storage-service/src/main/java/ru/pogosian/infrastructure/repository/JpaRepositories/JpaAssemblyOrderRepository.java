package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.AssemblyOrderJpaEntity;

import java.util.Optional;
import java.util.UUID;

public interface JpaAssemblyOrderRepository extends JpaRepository<AssemblyOrderJpaEntity, UUID> {
    Optional<AssemblyOrderJpaEntity> findBySourceOrderId(UUID sourceOrderId);
}
