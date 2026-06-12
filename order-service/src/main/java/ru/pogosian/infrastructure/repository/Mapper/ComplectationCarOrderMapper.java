package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.orders.complectationCarOrder.*;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder.ComplectationCarOrderStage;

@Component
@AllArgsConstructor
public class ComplectationCarOrderMapper {
    public ComplectationCarOrder toDomain(ComplectationCarOrderJpaEntity complectationCarOrderJpaEntity) {
        return ComplectationCarOrder.builder()
                .orderId(complectationCarOrderJpaEntity.getId())
                .clientId(complectationCarOrderJpaEntity.getClientId())
                .managerId(complectationCarOrderJpaEntity.getManagerId())
                .carId(complectationCarOrderJpaEntity.getCarId())
                .state(toDomain(complectationCarOrderJpaEntity.getStage()))
                .build();
    }

    public ComplectationCarOrderJpaEntity toJpaEntity(ComplectationCarOrder complectationCarOrder) {
        return new ComplectationCarOrderJpaEntity(
                complectationCarOrder.getOrderId(),
                complectationCarOrder.getClientId(),
                complectationCarOrder.getManagerId(),
                complectationCarOrder.getCarId(),
                toJpaEntity(complectationCarOrder.getState())
        );
    }

    private ComplectationCarOrderStage toJpaEntity(CompectationCarOrderStatusState complectationCarOrderStage) {
        if(complectationCarOrderStage instanceof ComplectationCarOrderApprovedByWarehouseState) {
            return ComplectationCarOrderStage.ApprovedByWarehouse;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderAwaitingForPaymen) {
            return ComplectationCarOrderStage.AwaitingForPayment;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderAwaitingForShipping) {
            return ComplectationCarOrderStage.AwaitingForShipping;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderCancelled) {
            return ComplectationCarOrderStage.Cancelled;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderCompleted) {
            return ComplectationCarOrderStage.Completed;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderIsReadyForPickingUp) {
            return ComplectationCarOrderStage.ReadyForPickingUp;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderPayed) {
            return ComplectationCarOrderStage.Payed;
        }
        if(complectationCarOrderStage instanceof ComplectationCarOrderPlaced) {
            return ComplectationCarOrderStage.Placed;
        }
        return null;
    }

    private CompectationCarOrderStatusState toDomain(ComplectationCarOrderStage complectationCarOrderStage) {
        if(complectationCarOrderStage == ComplectationCarOrderStage.ApprovedByWarehouse) {
            return new ComplectationCarOrderApprovedByWarehouseState();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForPayment) {
            return new ComplectationCarOrderAwaitingForPaymen();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForShipping) {
            return new ComplectationCarOrderAwaitingForShipping();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Cancelled) {
            return new ComplectationCarOrderCancelled();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Completed) {
            return new ComplectationCarOrderCompleted();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.ReadyForPickingUp) {
            return new ComplectationCarOrderIsReadyForPickingUp();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Payed) {
            return new  ComplectationCarOrderPayed();
        }
        if(complectationCarOrderStage == ComplectationCarOrderStage.Placed) {
            return new ComplectationCarOrderPlaced();
        }
        return null;
    }

}
