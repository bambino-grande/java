package ru.pogosian.presentation.DTO.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestDriveRequestResponse(
    boolean isCarCapableForTestDrive,
    UUID clientId,
    UUID carId,
    UUID modelId,
    LocalDateTime testDriveStartAt,
    UUID testDriveId
) {
}