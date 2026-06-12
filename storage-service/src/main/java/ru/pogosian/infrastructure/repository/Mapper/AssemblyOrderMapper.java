package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.assembly.AssemblyOrderStatus;
import ru.pogosian.business.repositories.CarModelRepository;
import ru.pogosian.business.repositories.CarRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.AssemblyOrderJpaEntity;
import ru.pogosian.messaging.OrderType;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AssemblyOrderMapper {
    private final CarModelRepository carModelRepository;
    public AssemblyOrder toDomain(AssemblyOrderJpaEntity jpaEntity) {

        return new AssemblyOrder(
            jpaEntity.getId(),
            jpaEntity.getSourceOrderId(),
            jpaEntity.getStatus(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt(),
            jpaEntity.isRemoved(),
            jpaEntity.getOrderType(),
            jpaEntity.getCarId(),
            jpaEntity.getRequiredDetailIds(),
            jpaEntity.getWarehouseEmployeeId()
        );
    }

    public AssemblyOrderJpaEntity toJpaEntity(AssemblyOrder assemblyOrder) {
        return new AssemblyOrderJpaEntity(
            assemblyOrder.getId(),
            assemblyOrder.getSourceOrderId(),
            assemblyOrder.getStatus(),
            assemblyOrder.getOrderType(),
            assemblyOrder.getCarId(),
            assemblyOrder.getRequiredDetailIds(),
            assemblyOrder.getWarehouseEmployeeId()
        );
    }
}
