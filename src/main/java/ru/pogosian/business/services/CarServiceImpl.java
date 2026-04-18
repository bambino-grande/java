package ru.pogosian.business.services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.cars.*;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.factories.*;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.filters.*;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{
    private final CarDetailsRepository carDetailsRepository;
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final CarConfigurationRepository carConfigurationRepository;

    @Transactional
    @Override
    public Car addCar(Car car) {
        carRepository.save(car);
        return car;
    }

    @Transactional
    @Override
    public Car updateCar(Car car) {
        carRepository.save(car);
        return car;
    }

    @Transactional
    @Override
    public void deleteCar(UUID carID) {
        carRepository.deleteById(carID);
    }

    @Override
    public Car viewCar(UUID carID) {
        return  carRepository.findById(carID);
    }

    @Override
    public List<Car> filteredCars(Filter.CarFilter carFilter) {
        return carRepository.findAllByFilter(carFilter);
    }

    @Transactional
    @Override
    public Car createCar(UUID configurationId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive) {
        Car car = Car.builder()
                .carId(UUID.randomUUID())
                .carName(carName)
                .configuration(carConfigurationRepository.findById(configurationId))
                .price(carConfigurationRepository.findById(configurationId).getTotalPrice())
                .color(color)
                .availableForSale(availableForSale)
                .availableForTestDrive(availableForTestDrive)
                .build();
        carRepository.save(car);
        return car;
    }

    @Transactional
    @Override
    public Car updateCar(UUID carId, UUID configurationId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive) {
        Car car = Car.builder()
                .carId(carId)
                .carName(carName)
                .configuration(carConfigurationRepository.findById(configurationId))
                .price(carConfigurationRepository.findById(configurationId).getTotalPrice())
                .color(color)
                .availableForSale(availableForSale)
                .availableForTestDrive(availableForTestDrive)
                .build();
        carRepository.save(car);
        return car;
    }

    @Transactional
    @Override
    public Car CreateCarFromModel(UUID modelId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive) {
        DetailFactory wheelFactory = new WheelDetailsFactory();
        DetailFactory interiorFactory = new InterirorFactory();
        DetailFactory transmissionFactory = new TransmissionDetailFactory();
        DetailFactory steeringWheelFactory = new SteeringWheelFactory();

        CarDetails wheel = wheelFactory.create("base wheels", Set.of(modelId), BigDecimal.ZERO);
        CarDetails interior = interiorFactory.create("base interiors", Set.of(modelId), BigDecimal.ZERO);
        CarDetails steeringWheel = steeringWheelFactory.create("base steeringWheels", Set.of(modelId), BigDecimal.ZERO);
        CarDetails transmission = transmissionFactory.create("base transmissions", Set.of(modelId), BigDecimal.ZERO);

        carDetailsRepository.save(wheel);
        carDetailsRepository.save(interior);
        carDetailsRepository.save(steeringWheel);
        carDetailsRepository.save(transmission);

        Set<CarDetails> baseDetails = Set.of(wheel, interior, transmission, steeringWheel);

        CarConfiguration base = CarConfiguration.builder()
                .configurationId(UUID.randomUUID())
                .configurationModelId(modelId)
                .totalPrice(carModelRepository.findById(modelId).getBasePrice())
                .usedDetails(baseDetails)
                .build();

        carConfigurationRepository.save(base);

        Car car = Car.builder()
                .carId(UUID.randomUUID())
                .carName(carName)
                .configuration(base)
                .price(base.getTotalPrice())
                .color(color)
                .availableForSale(availableForSale)
                .availableForTestDrive(availableForTestDrive)
                .build();
        carRepository.save(car);
        return car;
    }
}