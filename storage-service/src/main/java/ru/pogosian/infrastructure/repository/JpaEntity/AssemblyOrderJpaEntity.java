package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.assembly.AssemblyOrderStatus;
import ru.pogosian.messaging.OrderType;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "assembly_orders")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class AssemblyOrderJpaEntity extends BaseJpaEntity {
    @Column(nullable = false)
    UUID sourceOrderId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AssemblyOrderStatus status;

    @Column(nullable = false)
    UUID warehouseEmployeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderType orderType;

    @Column(nullable = false)
    UUID carId;

    @ElementCollection
    @CollectionTable(
            name = "assembly_order_required_details",
            joinColumns = @JoinColumn(name = "assembly_order_id")
    )
    @Column(name = "detail_id", nullable = false)
    Set<UUID> requiredDetailIds;

    public AssemblyOrderJpaEntity(UUID id, UUID sourceOrderId, AssemblyOrderStatus status, OrderType orderType, UUID carId, Set<UUID> requiredDetailIds, UUID warehouseEmployeeId) {
        super(id);
        this.sourceOrderId = sourceOrderId;
        this.status = status;
        this.warehouseEmployeeId = warehouseEmployeeId;
        this.orderType = orderType;
        this.carId = carId;
        this.requiredDetailIds = requiredDetailIds;
    }
}