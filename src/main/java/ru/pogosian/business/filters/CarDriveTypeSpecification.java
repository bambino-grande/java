package ru.pogosian.business.filters;

import lombok.AllArgsConstructor;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.DriveType;
import ru.pogosian.business.repositories.CarModelRepository;

@AllArgsConstructor
public class CarDriveTypeSpecification implements CarSpecification{
    private final DriveType driveType;
    private final CarModelRepository carModelRepository;
    @Override
    public boolean isSatisfied(Car car) {
        if(driveType != null) {
            if (carModelRepository.findById(car.getConfiguration().getConfigurationModelId()) == null ||
                    carModelRepository.findById(car.getConfiguration().getConfigurationModelId()).getDriveType() !=  driveType)
                return false;
        }
        return true;
    }
}
