package ru.pogosian.infrastructure.grpc.mapper;

import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.Car;
import ru.pogosian.grpc.storage.StorageCars;

import java.util.List;

@Component
public class StorageCarGrpcMapper {
    public StorageCars.ListAvailableCarsResponse toListAvailableCarsResponse(List<Car> cars) {
        return StorageCars.ListAvailableCarsResponse.newBuilder()
                .addAllCars(cars.stream().map(this::toGrpc).toList()).build();
    }

    public StorageCars.AvailableCarResponse toAvailableCarResponse(Car car) {
        return StorageCars.AvailableCarResponse.newBuilder().setCar(toGrpc(car)).build();
    }

    private StorageCars.AvailableCar toGrpc(Car car){
        return StorageCars.AvailableCar.newBuilder()
                .setCarId(car.getCarId().toString())
                .setCarName(car.getCarName())
                .setConfigurationId(car.getConfiguration().getConfigurationId().toString())
                .setConfigurationModelId(car.getConfiguration().getConfigurationModelId().toString())
                .setColor(car.getColor().name())
                .setPrice(car.getPrice().toString())
                .setAvailableForSale(car.getAvailableForSale())
                .setAvailableForTestDrive(car.getAvailableForTestDrive())
                .build();
    }


}
