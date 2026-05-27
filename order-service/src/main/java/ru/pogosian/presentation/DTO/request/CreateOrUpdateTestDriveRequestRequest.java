package ru.pogosian.presentation.DTO.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateOrUpdateTestDriveRequestRequest(
    boolean isCarCapableForTestDrive,
    UUID clientId,
    UUID carId,
    UUID modelId,
    LocalDateTime testDriveStartAt
) {
}
