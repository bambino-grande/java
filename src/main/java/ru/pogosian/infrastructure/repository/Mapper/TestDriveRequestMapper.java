package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.business.users.*;
import ru.pogosian.infrastructure.repository.JpaEntity.TestDriveRequestJpaEntity;

@Component
@AllArgsConstructor
public class TestDriveRequestMapper {
    public TestDriveRequest toDomain(TestDriveRequestJpaEntity testDriveRequestJpaEntity) {
        return TestDriveRequest.builder()
                .testDriveId(testDriveRequestJpaEntity.getId())
                .isCarCapableForTestDrive(testDriveRequestJpaEntity.isCarCapableForTestDrive())
                .testDriveStartAt(testDriveRequestJpaEntity.getTestDriveStartAt())
                .modelId(testDriveRequestJpaEntity.getModelId())
                .carId(testDriveRequestJpaEntity.getCarId())
                .clientId(testDriveRequestJpaEntity.getClientId())
                .build();
    }
    public TestDriveRequestJpaEntity toJpaEntity(TestDriveRequest testDriveRequest) {
        return new TestDriveRequestJpaEntity(
                testDriveRequest.getCarId(),
                testDriveRequest.isCarCapableForTestDrive(),
                testDriveRequest.getTestDriveId(),
                testDriveRequest.getClientId(),
                testDriveRequest.getModelId(),
                testDriveRequest.getTestDriveStartAt()
        );
    }
}
