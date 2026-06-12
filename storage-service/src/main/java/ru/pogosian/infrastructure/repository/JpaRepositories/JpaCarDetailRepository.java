package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;

import java.util.UUID;

public interface JpaCarDetailRepository extends JpaRepository<CarDetailJpaEntity, UUID> {
}
