package ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.orders.complectationCarOrder.CompectationCarOrderStatusState;
import ru.pogosian.infrastructure.repository.JpaEntity.BaseJpaEntity;

import java.util.UUID;

@Getter
@Entity
@Table(name = "complectation_car_orders")
@SQLRestriction("removed = false")
public class ComplectationCarOrderJpaEntity extends BaseJpaEntity {
    @Column
    private UUID clientId;

    @Column
    private UUID managerId;

    @Column
    private UUID carId;

    @Enumerated(EnumType.STRING)
    @Column
    private ComplectationCarOrderStage stage;

    public ComplectationCarOrderJpaEntity(UUID id, UUID clientId, UUID managerId, UUID carId, ComplectationCarOrderStage state) {
        super(id);
        this.clientId = clientId;
        this.managerId = managerId;
        this.carId = carId;
        this.stage = state;
    }
}
