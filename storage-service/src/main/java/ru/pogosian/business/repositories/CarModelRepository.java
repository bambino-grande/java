package ru.pogosian.business.repositories;

import ru.pogosian.business.cars.CarModel;

import java.util.UUID;
import java.util.List;

public interface CarModelRepository {
    void save(CarModel carModel);
    CarModel findById(UUID id);
    List<CarModel> findAll();
    void deleteById(UUID id);
}