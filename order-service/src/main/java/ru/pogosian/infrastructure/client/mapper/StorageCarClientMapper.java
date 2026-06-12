package ru.pogosian.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import ru.pogosian.grpc.storage.StorageCars;
import ru.pogosian.infrastructure.client.StorageCarClient;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class StorageCarClientMapper {
    public StorageCarClient.AvailableCar toClientCar(StorageCars.AvailableCar car) {
        return new StorageCarClient.AvailableCar(
                UUID.fromString(car.getCarId()),
                car.getCarName(),
                UUID.fromString(car.getConfigurationId()),
                UUID.fromString(car.getConfigurationModelId()),
                car.getColor(),
                new BigDecimal(car.getPrice()),
                car.getAvailableForSale(),
                car.getAvailableForTestDrive()
        );
    }
}
