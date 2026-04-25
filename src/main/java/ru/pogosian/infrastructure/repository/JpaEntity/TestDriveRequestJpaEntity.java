package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.testDrive.TestDriveRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "test_drive_request")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class TestDriveRequestJpaEntity extends BaseJpaEntity {
    @Column(nullable = false)
    private boolean isCarCapableForTestDrive;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private UUID carId;

    @Column(nullable = false)
    private UUID modelId;

    @Column(nullable = false)
    private LocalDateTime testDriveStartAt;

    public TestDriveRequestJpaEntity(UUID id, boolean isCarCapableForTestDrive, UUID clientId, UUID carId, UUID modelId, LocalDateTime testDriveStartAt) {
        super(id);
        this.isCarCapableForTestDrive = isCarCapableForTestDrive;
        this.clientId = clientId;
        this.carId = carId;
        this.modelId = modelId;
        this.testDriveStartAt = testDriveStartAt;
    }
}
