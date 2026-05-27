package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.infrastructure.repository.JpaEntity.CarModelJpaEntity;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CarModelMapper {
    DetailMapper detailMapper;
    public CarModel toDomain(CarModelJpaEntity carModelJpaEntity) {
        return CarModel.builder()
                .modelId(carModelJpaEntity.getId())
                .modelBrand(carModelJpaEntity.getModelBrand())
                .modelName(carModelJpaEntity.getModelName())
                .bodyType(carModelJpaEntity.getBodyType())
                .availableDetails(carModelJpaEntity.getAvailableDetails().stream().map(detailMapper::toDomain).collect(Collectors.toSet()))
                .details(carModelJpaEntity.getDetails().stream().map(detailMapper::toDomain).collect(Collectors.toSet()))
                .basePrice(carModelJpaEntity.getBasePrice())
                .fuelType(carModelJpaEntity.getFuelType())
                .horsePower(carModelJpaEntity.getHorsePower())
                .engineVolume(carModelJpaEntity.getEngineVolume())
                .gearboxType(carModelJpaEntity.getGearboxType())
                .driveType(carModelJpaEntity.getDriveType())
                .build();
    }

    public CarModelJpaEntity toJpaEntity(CarModel carModel) {
        return new CarModelJpaEntity(
                carModel.getModelId(),
                carModel.getModelBrand(),
                carModel.getModelName(),
                carModel.getBodyType(),
                carModel.getAvailableDetails().stream().map(detailMapper::toJpaEntity).collect(Collectors.toSet()),
                carModel.getBasePrice(),
                carModel.getFuelType(),
                carModel.getHorsePower(),
                carModel.getEngineVolume(),
                carModel.getGearboxType(),
                carModel.getDriveType(),
                carModel.getDetails().stream().map(detailMapper::toJpaEntity).collect(Collectors.toSet())
        );
    }
}
