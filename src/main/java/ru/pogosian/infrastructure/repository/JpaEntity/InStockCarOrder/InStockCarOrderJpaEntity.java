package ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderStatusState;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.util.UUID;

@Getter
@Entity
@Table(name = "in_stock_car_orders")
@SQLRestriction("removed = false")
public class InStockCarOrderJpaEntity extends BaseJpaEntity {
    @Column
    private UUID clientId;

    @Column
    private UUID managerId;

    @Column
    private UUID carId;

    @Enumerated(EnumType.STRING)
    @Column
    private InStockCarOrderStage stage;

    public InStockCarOrderJpaEntity(UUID id, UUID clientId, UUID managerId, UUID carId, InStockCarOrderStage stage) {
        super(id);
        this.clientId = clientId;
        this.managerId = managerId;
        this.carId = carId;
        this.stage = stage;
    }
}
