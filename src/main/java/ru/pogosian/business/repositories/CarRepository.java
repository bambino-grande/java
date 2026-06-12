package ru.pogosian.business.repositories;

import ru.pogosian.business.cars.Car;

import java.util.UUID;
import java.util.List;

public interface CarRepository {
    void save(Car car);
    Car findById(UUID id);
    List<Car> findAll();
    void deleteById(UUID id);
}
