package ru.pogosian.business.services;

import ru.pogosian.business.cars.Car;
import ru.pogosian.business.filters.Filter;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface CarService {
    Car addCar(Car Car);
    Car updateCar(Car car);
    void deleteCar(UUID carID);
    Car viewCar(UUID carID);
    List<Car> filteredCars(Filter.CarFilter carFilter);
    Car createCar(UUID configurationId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive);
    Car updateCar(UUID carId, UUID configurationId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive);
    Car CreateCarFromModel(UUID modelId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive);
}