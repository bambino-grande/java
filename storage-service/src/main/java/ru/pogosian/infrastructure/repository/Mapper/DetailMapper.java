package ru.pogosian.infrastructure.repository.Mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Interior;
import ru.pogosian.business.detail.types.SteeringWheel;
import ru.pogosian.business.detail.types.Transmisson;
import ru.pogosian.business.detail.types.Wheel;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.CarDetail.CarDetailTypes;

@Component
@AllArgsConstructor
public class DetailMapper {
    public CarDetails toDomain(CarDetailJpaEntity detailJpaEntity) {
        if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Interior) {
            return new Interior(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.SteeringWheel) {
            return new SteeringWheel(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Transmission) {
            return new Transmisson(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else if (detailJpaEntity.getDetailTypes() == CarDetailTypes.Wheel) {
            return new Wheel(
                    detailJpaEntity.getName(),
                    detailJpaEntity.getCompatibleModelsIds(),
                    detailJpaEntity.getDeltaPrice(),
                    detailJpaEntity.getId()
            );
        } else
            return null;
    }

    private CarDetailTypes getDetailType(CarDetails detail) {
        if (detail instanceof Interior)
            return CarDetailTypes.Interior;
        else if (detail instanceof SteeringWheel)
            return CarDetailTypes.SteeringWheel;
        else if (detail instanceof Transmisson)
            return CarDetailTypes.Transmission;
        else if (detail instanceof Wheel)
            return CarDetailTypes.Wheel;
        return null;
    }

    public CarDetailJpaEntity toJpaEntity(CarDetails details) {
        return new CarDetailJpaEntity(
                details.getId(),
                details.getName(),
                details.getDeltaPrice(),
                details.getCompatibleModelsIds(),
                getDetailType(details)
        );
    }

}
