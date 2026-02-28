package ru.pogosian.business.testDrive;

import lombok.Getter;
import lombok.Builder;

import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@Builder
public class TestDriveRequest {
    private boolean isCarCapableForTestDrive;
    private UUID  testDriveId;
    private UUID clientId;
    private UUID cartId;
    private UUID modelId;
    private LocalDateTime testDriveStartAt;
}
