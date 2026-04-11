package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaTestDriveRequestRepository extends JpaRepository<TestDriveRequestJpaEntity, UUID> {
    Page<TestDriveRequestJpaEntity> findAllByRemovedFalse(Pageable pageable);
    Optional<TestDriveRequestJpaEntity> findByIdAndRemovedFalse(UUID id);
    boolean existsByIdAndRemovedFalse(UUID id);
}
