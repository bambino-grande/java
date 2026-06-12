package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.assembly.AssemblyOrderStatus;
import ru.pogosian.infrastructure.repository.JpaEntity.AssemblyOrderJpaEntity;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateAssemblyOrderRequest;
import ru.pogosian.presentation.DTO.response.AssemblyOrderResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssemblyOrderMapper {
    AssemblyOrderResponse toDto(AssemblyOrder entity);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "status", expression = "java(resolveStatus(request.status()))")
    AssemblyOrder toDomain(CreateOrUpdateAssemblyOrderRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", expression = "java(resolveStatus(request.status()))")
    AssemblyOrder toDomain(CreateOrUpdateAssemblyOrderRequest request, UUID id);

    default AssemblyOrderStatus resolveStatus(String status) {
        return status == null ? AssemblyOrderStatus.CREATED : AssemblyOrderStatus.valueOf(status);
    }
}
