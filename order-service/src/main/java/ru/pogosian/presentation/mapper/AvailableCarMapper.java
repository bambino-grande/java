package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.presentation.DTO.response.AvailableCarResponse;
import ru.pogosian.infrastructure.client.StorageCarClient;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvailableCarMapper {
    AvailableCarResponse toDto(StorageCarClient.AvailableCar car);
}
