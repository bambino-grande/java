package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.Car;
import ru.pogosian.infrastructure.repository.JpaEntity.CarJpaEntity;

@Component
@AllArgsConstructor
public class CarMapper {
    CarConfigurationMapper carConfigurationMapper;
    public Car toDomain(CarJpaEntity carJpaEntity) {
        return Car.builder()
                .carId(carJpaEntity.getId())
                .carName(carJpaEntity.getCarName())
                .configuration(carConfigurationMapper.toDomain(carJpaEntity.getConfiguration()))
                .color(carJpaEntity.getColor())
                .price(carJpaEntity.getPrice())
                .availableForSale(carJpaEntity.getAvailableForSale())
                .availableForTestDrive(carJpaEntity.getAvailableForTestDrive())
                .build();
    }

    public CarJpaEntity toJpaEntity(Car car) {
        return new CarJpaEntity(car.getCarId(), car.getCarName(), carConfigurationMapper.toJpaEntity(car.getConfiguration()), car.getColor(), car.getPrice(), car.getAvailableForSale(), car.getAvailableForTestDrive());
    }
}
