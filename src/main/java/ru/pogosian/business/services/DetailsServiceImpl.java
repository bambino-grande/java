package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarDetailsRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailService{
    private final CarDetailsRepository carDetailsRepository;
    @Override
    public CarDetails addCarDetails(CarDetails carDetail) {
            carDetailsRepository.save(carDetail);
            return carDetail;
    }

    @Override
    public CarDetails updateCarDetails(CarDetails carDetail) {
        carDetailsRepository.save(carDetail);
        return carDetail;
    }

    @Override
    public void deleteCar(UUID carDetailID) {
        carDetailsRepository.deleteById(carDetailID);
    }

    @Override
    public CarDetails viewCarDetails(UUID carDetailID) {
        return carDetailsRepository.findById(carDetailID);
    }

    @Override
    public List<CarDetails> viewAllCars() {
        return carDetailsRepository.findAll();
    }
}
