package ru.pogosian.infrastructure.repository.Mapper;

import org.springframework.stereotype.Component;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.infrastructure.repository.JpaEntity.AssemblyOrderJpaEntity;

@Component
public class AssemblyOrderMapper {
    public AssemblyOrder toDomain(AssemblyOrderJpaEntity jpaEntity) {
        return AssemblyOrder.builder()
                .id(jpaEntity.getId())
                .sourceOrderId(jpaEntity.getSourceOrderId())
                .status(jpaEntity.getStatus())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .removed(jpaEntity.isRemoved())
                .build();
    }

    public AssemblyOrderJpaEntity toJpaEntity(AssemblyOrder assemblyOrder) {
        return new AssemblyOrderJpaEntity(assemblyOrder.getId(),
                assemblyOrder.getSourceOrderId(),
                assemblyOrder.getStatus());
    }
}
