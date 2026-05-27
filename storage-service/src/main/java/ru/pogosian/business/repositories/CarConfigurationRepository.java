package ru.pogosian.business.repositories;

import ru.pogosian.business.cars.CarConfiguration;

import java.util.UUID;
import java.util.List;

public interface CarConfigurationRepository {
    void save(CarConfiguration configuration);
    CarConfiguration findById(UUID id);
    List<CarConfiguration> findAll();
    void deleteById(UUID id);
}