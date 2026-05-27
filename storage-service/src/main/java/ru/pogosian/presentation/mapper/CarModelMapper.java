package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarModelRequest;
import ru.pogosian.presentation.DTO.response.CarModelResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = CarDetailMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarModelMapper {
    CarModelResponse toDto(CarModel carModel);

    @Mapping(target = "modelId", ignore = true)
    CarModel toDomain(CreateOrUpdateCarModelRequest request);

    @Mapping(target = "modelId", source = "modelId")
    CarModel toDomain(CreateOrUpdateCarModelRequest request, UUID modelId);

}
