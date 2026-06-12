package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.FuelType;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarFuelTypeSpecification implements CarSpecification{
    private final FuelType fuelType;
    private final CarModelRepository carModelRepository;
    @Override
    public boolean isSatisfied(Car car) {
        if(fuelType != null) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getFuelType() !=  fuelType)
                return false;
        }
        return true;
    }
}