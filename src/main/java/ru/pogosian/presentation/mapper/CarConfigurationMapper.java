package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateCarConfigurationRequest;
import ru.pogosian.presentation.DTO.response.CarConfigurationResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = CarDetailMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarConfigurationMapper {
    @Mapping(target = "carConfigurationId", source = "configurationId")
    CarConfigurationResponse toDto(CarConfiguration configuration);

    @Mapping(target = "configurationId", ignore = true)
    CarConfiguration toDomain(CreateOrUpdateCarConfigurationRequest request);

    @Mapping(target = "configurationId", source = "configurationId")
    CarConfiguration toDomain(CreateOrUpdateCarConfigurationRequest request, UUID configurationId);
}
