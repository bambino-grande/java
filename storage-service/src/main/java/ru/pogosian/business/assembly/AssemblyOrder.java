package ru.pogosian.business.assembly;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.query.Order;
import ru.pogosian.messaging.OrderType;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AssemblyOrder {
    private UUID id;
    private UUID sourceOrderId;
    private AssemblyOrderStatus status;
    Instant createdAt;
    Instant updatedAt;
    boolean removed;
    OrderType orderType;
    UUID carId;
    Set<UUID> requiredDetailIds;
    UUID warehouseEmployeeId;


    public AssemblyOrder(UUID id, UUID sourceOrderId, AssemblyOrderStatus status, boolean removed, OrderType orderType, UUID carId,  Set<UUID> requiredDetailIds, UUID warehouseEmployeeId) {
        this.id = id;
        this.sourceOrderId = sourceOrderId;
        this.orderType = orderType;
        this.carId = carId;
        this.requiredDetailIds = requiredDetailIds;
        this.warehouseEmployeeId = warehouseEmployeeId;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.removed = removed;
    }
}