package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarMinHorsePowerSpecification implements CarSpecification{
    private final int minHorsePower;
    private final CarModelRepository carModelRepository;

    @Override
    public boolean isSatisfied(Car car) {
        if(minHorsePower != 0) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getHorsePower() < minHorsePower)
                return false;
        }
        return true;
    }
}