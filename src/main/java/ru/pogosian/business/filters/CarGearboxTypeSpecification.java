package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.GearboxType;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarGearboxTypeSpecification implements CarSpecification{
    private final GearboxType gearboxType;
    private final CarModelRepository carModelRepository;
    @Override
    public boolean isSatisfied(Car car) {
        if(gearboxType != null) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getGearboxType() !=  gearboxType)
                return false;
        }
        return true;
    }
}
