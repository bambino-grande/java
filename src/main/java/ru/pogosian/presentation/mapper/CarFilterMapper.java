package ru.pogosian.presentation.mapper;

import org.mapstruct.*;
import ru.pogosian.business.filters.Filter;
import ru.pogosian.presentation.DTO.CarFilterDto;

import java.awt.*;
import java.util.Set;

@Mapper(componentModel = "spring", uses = ColorConverter.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarFilterMapper {
    @Mapping(target = "color", source = "color", qualifiedByName = "StringToColorSet")
    Filter.CarFilter toDomain(CarFilterDto request);

    @Mapping(target = "color", source = "color", qualifiedByName = "ColorToStringSet")
    CarFilterDto toDto(Filter.CarFilter request);

    @Named("StringToColorSet")
    @IterableMapping(qualifiedByName = "StringToColor")
    Set<Color> StringToColorSet(Set<String> colorSet);

    @Named("ColorToStringSet")
    @IterableMapping(qualifiedByName = "ColorToString")
    Set<String> ColorToStringSet(Set<Color> colorSet);
}
