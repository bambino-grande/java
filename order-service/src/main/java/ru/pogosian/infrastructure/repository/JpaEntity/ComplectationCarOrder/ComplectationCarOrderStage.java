package ru.pogosian.infrastructure.repository.JpaEntity.ComplectationCarOrder;

public enum ComplectationCarOrderStage {
    ApprovedByWarehouse,
    AwaitingForPayment,
    AwaitingForShipping,
    Cancelled,
    Completed,
    ReadyForPickingUp,
    Payed,
    Placed
}
