package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "test_drive_request")
public class TestDriveRequestJpaEntity extends BaseJpaEntity {
    @Column(nullable = false)
    private boolean isCarCapableForTestDrive;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private UUID cartId;

    @Column(nullable = false)
    private UUID modelId;

    @Column(nullable = false)
    private LocalDateTime testDriveStartAt;
}
