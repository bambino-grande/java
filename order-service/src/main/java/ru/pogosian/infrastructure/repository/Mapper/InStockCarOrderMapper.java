package ru.pogosian.infrastructure.repository.Mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pogosian.business.orders.inStockCarOrder.*;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderJpaEntity;
import ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder.InStockCarOrderStage;

@Component
@AllArgsConstructor
public class InStockCarOrderMapper {
    public InStockCarOrder toDomain(InStockCarOrderJpaEntity inStockCarOrderJpaEntity) {
        return InStockCarOrder.builder()
                .orderId(inStockCarOrderJpaEntity.getId())
                .carId(inStockCarOrderJpaEntity.getCarId())
                .clientId(inStockCarOrderJpaEntity.getClientId())
                .managerId(inStockCarOrderJpaEntity.getManagerId())
                .state(toDomain(inStockCarOrderJpaEntity.getStage()))
                .build();
    }

    public InStockCarOrderJpaEntity toJpaEntity(InStockCarOrder inStockCarOrder) {
        return new InStockCarOrderJpaEntity(
                inStockCarOrder.getOrderId(),
                inStockCarOrder.getClientId(),
                inStockCarOrder.getManagerId(),
                inStockCarOrder.getCarId(),
                toJpaEntity(inStockCarOrder.getState())
        );
    }

    private InStockCarOrderStatusState toDomain(InStockCarOrderStage inStockCarOrderStage) {
        if(inStockCarOrderStage == InStockCarOrderStage.ApprovedByManager) {
            return new InStockCarOrderApprovedByManager();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.AwaitingForPayment) {
            return new InStockCarOrderAwaitingForPaymen();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Cancelled) {
            return new InStockCarOrderCancelled();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Completed) {
            return new  InStockCarOrderCompleted();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.ReadyForPickingUp) {
            return new InStockCarOrderIsReadyForPickingUp();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Payed) {
            return new InStockCarOrderPayed();
        }
        if(inStockCarOrderStage == InStockCarOrderStage.Placed) {
            return new InStockCarOrderPlaced();
        }
        return null;
    }

    private InStockCarOrderStage toJpaEntity(InStockCarOrderStatusState inStockCarOrderStage) {
        if(inStockCarOrderStage instanceof InStockCarOrderApprovedByManager) {
            return InStockCarOrderStage.ApprovedByManager;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderAwaitingForPaymen) {
            return InStockCarOrderStage.AwaitingForPayment;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderCancelled) {
            return InStockCarOrderStage.Cancelled;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderCompleted) {
            return InStockCarOrderStage.Completed;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderIsReadyForPickingUp) {
            return InStockCarOrderStage.ReadyForPickingUp;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderPayed) {
            return InStockCarOrderStage.Payed;
        }
        if(inStockCarOrderStage instanceof InStockCarOrderPlaced) {
            return InStockCarOrderStage.Placed;
        }
        return null;
    }

}
