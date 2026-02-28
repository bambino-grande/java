package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import ru.pogosian.business.cars.*;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.factories.*;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.repositories.CarDetailsRepository;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class CarServiceImpl implements CarService{
    private final CarDetailsRepository carDetailsRepository;
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;

    @Override
    public Car addCar(Car car) {
        carRepository.save(car);
        return car;
    }

    @Override
    public Car updateCar(Car car) {
        if(carRepository.findById(car.getCarId()) == null)
            throw new DomainValidationException("Car with id " + car.getCarId() + " not found");
        carRepository.save(car);
        return car;
    }

    @Override
    public void deleteCar(UUID carID) {
        if(carRepository.findById(carID) ==  null)
            throw new DomainValidationException("Car with id " + carID + " not found");
        carRepository.deleteById(carID);
    }

    @Override
    public Car viewCar(UUID carID) {
        if(carRepository.findById(carID) ==  null)
            throw new DomainValidationException("Car with id " + carID + " not found");
        return  carRepository.findById(carID);
    }

    @Override
    public List<Car> filteredCars(CarFilter carFilter) {
        List<Car> alreadyFilteredCars = new ArrayList<>();
        for(var car : carRepository.findAll()) {
            if(carFilter.getMinPrice() != null) {
                if (car.getPrice().compareTo(carFilter.getMinPrice()) < 0)
                    continue;
            }
            if(carFilter.getMaxPrice() != null) {
                if (car.getPrice().compareTo(carFilter.getMaxPrice()) > 0)
                    continue;
            }
            if(carFilter.getMinPrice() != null) {
                if (car.getPrice().compareTo(carFilter.getMinPrice()) < 0)
                    continue;
            }
            if(carFilter.getColor() != null) {
                if (!carFilter.getColor().contains(car.getColor()))
                    continue;
            }
            if(carFilter.getModelName() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        !carFilter.getModelName().contains(carModelRepository.findById(car.getConfiguration().getConfigurationId()).getModelName()))
                    continue;
            }
            if(carFilter.getModelBrand() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        !carFilter.getModelBrand().contains(carModelRepository.findById(car.getConfiguration().getConfigurationId()).getModelBrand()))
                    continue;
            }
            if(carFilter.getMinEngineVolume() != 0) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getHorsePower() < carFilter.getMinEngineVolume())
                    continue;
            }
            if(carFilter.getMaxHorsePower() != 0) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getHorsePower() < carFilter.getMaxHorsePower())
                    continue;
            }
            if(carFilter.getMinEngineVolume() != 0) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getEngineVolume() < carFilter.getMinHorsePower())
                    continue;
            }
            if(carFilter.getMaxEngineVolume() != 0) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getEngineVolume() < carFilter.getMaxHorsePower())
                    continue;
            }
            if(carFilter.getGearboxType() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getGearboxType() !=  carFilter.getGearboxType())
                    continue;
            }
            if(carFilter.getDriveType() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getDriveType() !=  carFilter.getDriveType())
                    continue;
            }
            if(carFilter.getBodyType() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getBodyType() !=  carFilter.getBodyType())
                    continue;
            }
            if(carFilter.getFuelType() != null) {
                if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                        carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getFuelType() !=  carFilter.getFuelType())
                    continue;
            }
            alreadyFilteredCars.add(car);
        }
        return alreadyFilteredCars;
    }

    @Override
    public Car CreateCarFromModel(UUID modelId, String carName, Color color, boolean availableForSale, boolean availableForTestDrive) {
        if(modelId == null)
            throw new DomainValidationException("modelId is null");

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
                .build();

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