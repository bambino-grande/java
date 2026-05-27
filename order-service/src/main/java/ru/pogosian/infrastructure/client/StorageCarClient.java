package ru.pogosian.infrastructure.client;

import java.util.UUID;

public interface StorageCarClient {
    record CarInstance(
            UUID carId,
            UUID configurationModelId,
            Boolean availableForTestDrive
    ){}

    CarInstance getCar(UUID carId);
    void makeCarAvailableForTestDrive(UUID carId, boolean availableForTestDrive);
}
