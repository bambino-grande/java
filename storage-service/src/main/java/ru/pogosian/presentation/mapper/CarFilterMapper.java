package ru.pogosian.presentation.mapper;

import org.mapstruct.*;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.presentation.DTO.CarFilterDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarFilterMapper {
    Filter.CarFilter toDomain(CarFilterDto request);

    CarFilterDto toDto(Filter.CarFilter request);
}
