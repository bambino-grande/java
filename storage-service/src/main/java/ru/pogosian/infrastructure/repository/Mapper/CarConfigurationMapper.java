package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.infrastructure.repository.JpaEntity.CarConfigurationJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CarConfigurationMapper {
    DetailMapper detailMapper;
    public CarConfiguration toDomain(CarConfigurationJpaEntity carConfigurationJpaEntity) {
        return CarConfiguration.builder()
                .configurationId(carConfigurationJpaEntity.getId())
                .configurationModelId(carConfigurationJpaEntity.getConfigurationModelId())
                .totalPrice(carConfigurationJpaEntity.getTotalPrice())
                .usedDetails(carConfigurationJpaEntity.getUsedDetails().stream().map(detailMapper::toDomain).collect(Collectors.toSet()))
                .build();
    }

    public CarConfigurationJpaEntity toJpaEntity(CarConfiguration carConfiguration) {
        return new CarConfigurationJpaEntity(
                carConfiguration.getConfigurationId(),
                carConfiguration.getConfigurationModelId(),
                carConfiguration.getTotalPrice(),
                carConfiguration.getUsedDetails().stream().map(detailMapper::toJpaEntity).collect(Collectors.toSet())
        );
    }
    public CarConfigurationJpaEntity toJpaEntity(CarConfiguration carConfiguration, Set<CarDetailJpaEntity> carDetail) {
        return new CarConfigurationJpaEntity(
                carConfiguration.getConfigurationId(),
                carConfiguration.getConfigurationModelId(),
                carConfiguration.getTotalPrice(),
                carDetail
        );
    }
}
