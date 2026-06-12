package ru.pogosian.presentation.mapper;

import org.mapstruct.*;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.presentation.DTO.CarFilterDto;

import java.awt.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarFilterMapper {
    Filter.CarFilter toDomain(CarFilterDto request);

    CarFilterDto toDto(Filter.CarFilter request);
}
