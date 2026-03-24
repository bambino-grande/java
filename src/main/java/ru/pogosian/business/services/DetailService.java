package ru.pogosian.business.services;

import ru.pogosian.business.detail.CarDetails;

import java.util.List;
import java.util.UUID;

public interface DetailService {
    CarDetails addCarDetails(CarDetails carDetails);
    CarDetails updateCarDetails(CarDetails carDetails);
    void deleteCar(UUID carDetailID);
    CarDetails viewCarDetails(UUID carDetailID);
    List<CarDetails> viewAllCars();
}