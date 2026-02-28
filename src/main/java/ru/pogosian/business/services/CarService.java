package ru.pogosian.business.services;

import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarFilter;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface CarService {
    Car addCar(Car Car);
    Car updateCar(Car car);
    void deleteCar(UUID carID);
    Car viewCar(UUID carID);
    List<Car> filteredCars(CarFilter carFilter);
    Car CreateCarFromModel(UUID moelId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive);
}