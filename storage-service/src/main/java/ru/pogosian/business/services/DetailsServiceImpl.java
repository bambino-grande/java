package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.repositories.CarDetailsRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailService{
    private final CarDetailsRepository carDetailsRepository;
    @Transactional
    @Override
    public CarDetails addCarDetails(CarDetails carDetail) {
            carDetailsRepository.save(carDetail);
            return carDetail;
    }
    @Transactional
    @Override
    public CarDetails updateCarDetails(CarDetails carDetail) {
        carDetailsRepository.save(carDetail);
        return carDetail;
    }
    @Transactional
    @Override
    public void deleteCar(UUID carDetailID) {
        carDetailsRepository.deleteById(carDetailID);
    }

    @Override
    public CarDetails viewCarDetails(UUID carDetailID) {
        return carDetailsRepository.findById(carDetailID);
    }

    @Override
    public List<CarDetails> viewAllCars(Pageable pageable) {
        return carDetailsRepository.findAll(pageable);
    }
}
