package ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "in_stock_car_orders")
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
}
