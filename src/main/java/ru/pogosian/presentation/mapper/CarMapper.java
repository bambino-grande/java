package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.cars.Car;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.presentation.DTO.request.CreateCarFromModelRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarRequest;
import ru.pogosian.presentation.DTO.response.CarResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CarConfigurationMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarResponse toDto(Car car);

    @Mapping(target = "carId", ignore = true)
    @Mapping(target = "configuration", source = "configuration")
    @Mapping(target = "price", source = "configuration.totalPrice")
    Car toDomain(CreateOrUpdateCarRequest request, CarConfiguration configuration);

    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "configuration", source = "configuration")
    @Mapping(target = "price", source = "configuration.totalPrice")
    Car toDomain(CreateOrUpdateCarRequest request, UUID carId, CarConfiguration configuration);

    @Mapping(target = "carId", ignore = true)
    @Mapping(target = "configuration", source = "configuration")
    @Mapping(target = "price", source = "configuration.totalPrice")
    Car toDomain(CreateCarFromModelRequest request, CarConfiguration configuration);

    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "configuration", source = "configuration")
    @Mapping(target = "price", source = "configuration.totalPrice")
    Car toDomain(CreateCarFromModelRequest request, UUID carId, CarConfiguration configuration);
}