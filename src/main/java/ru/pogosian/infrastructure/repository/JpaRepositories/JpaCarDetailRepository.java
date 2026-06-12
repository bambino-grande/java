package ru.pogosian.infrastructure.repository.JpaRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.User.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCarDetailRepository extends JpaRepository<CarDetailJpaEntity, UUID> {
}
