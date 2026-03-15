package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.BodyType;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarBodyTypeSpecification implements CarSpecification{
    private final BodyType bodyType;
    private final CarModelRepository carModelRepository;
    @Override
    public boolean isSatisfied(Car car) {
        if(bodyType != null) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getBodyType() !=  bodyType)
                return false;
        }
        return true;
    }
}