package ru.pogosian.infrastructure.client;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface StorageCarClient {
    record CarInstance(
            UUID carId,
            UUID configurationModelId,
            Boolean availableForTestDrive
    ){}

    record AvailableCar(
        UUID carId,
        String carName,
        UUID configurationId,
        UUID  configurationModelId,
        String color,
        BigDecimal price,
        Boolean availableForSale,
        Boolean availableForTestDrive
    ){}

    CarInstance getCar(UUID carId);
    void makeCarAvailableForTestDrive(UUID carId, boolean availableForTestDrive);
    List<AvailableCar> getAvailableCars();
    AvailableCar getAvailableCar(UUID carId);
}
