package ru.pogosian.business.repositories;

import ru.pogosian.business.detail.CarDetails;

import java.util.UUID;
import java.util.List;

public interface CarDetailsRepository {
    void save(CarDetails carModel);
    CarDetails findById(UUID id);
    List<CarDetails> findAll();
    void deleteById(UUID id);
}