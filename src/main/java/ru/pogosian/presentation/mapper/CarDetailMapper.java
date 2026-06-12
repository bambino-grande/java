package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.detail.types.Interior;
import ru.pogosian.business.detail.types.SteeringWheel;
import ru.pogosian.business.detail.types.Transmisson;
import ru.pogosian.business.detail.types.Wheel;
import ru.pogosian.presentation.DTO.Types.CarDetailTypes;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarDetailRequest;
import ru.pogosian.presentation.DTO.response.CarDetailResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarDetailMapper {
    @Mapping(target = "carDetailTypes", source = ".", qualifiedByName = "toDtoType")
    CarDetailResponse toDto(CarDetails carDetails);

    default CarDetails toDomain(CreateOrUpdateCarDetailRequest request, UUID id){
        if(request.carDetailTypes() == CarDetailTypes.Interior)
            return new Interior(request.name(), request.compatibleModelsIds(), request.deltaPrice(), id);
        if(request.carDetailTypes() == CarDetailTypes.Wheel)
            return new Wheel(request.name(), request.compatibleModelsIds(), request.deltaPrice(), id);
        if(request.carDetailTypes() == CarDetailTypes.Transmission)
            return new Transmisson(request.name(), request.compatibleModelsIds(), request.deltaPrice(), id);
        if(request.carDetailTypes() == CarDetailTypes.SteeringWheel)
            return new SteeringWheel(request.name(), request.compatibleModelsIds(), request.deltaPrice(), id);
        throw new IllegalArgumentException("invalid request");
    }

    default CarDetails toDomain(CreateOrUpdateCarDetailRequest request){
        if(request.carDetailTypes() == CarDetailTypes.Interior)
            return new Interior(request.name(), request.compatibleModelsIds(), request.deltaPrice());
        if(request.carDetailTypes() == CarDetailTypes.Wheel)
            return new Wheel(request.name(), request.compatibleModelsIds(), request.deltaPrice());
        if(request.carDetailTypes() == CarDetailTypes.Transmission)
            return new Transmisson(request.name(), request.compatibleModelsIds(), request.deltaPrice());
        if(request.carDetailTypes() == CarDetailTypes.SteeringWheel)
            return new SteeringWheel(request.name(), request.compatibleModelsIds(), request.deltaPrice());
        throw new IllegalArgumentException("invalid request");
    }

    @Named("toDtoType")
    default CarDetailTypes toDtoType(CarDetails carDetails){
        if (carDetails instanceof Interior)
            return CarDetailTypes.Interior;
        if (carDetails instanceof SteeringWheel)
            return CarDetailTypes.SteeringWheel;
        if (carDetails instanceof Wheel)
            return CarDetailTypes.Wheel;
        if (carDetails instanceof Transmisson)
            return CarDetailTypes.Transmission;
        throw new IllegalArgumentException("Unknown car detail type");
    }
}