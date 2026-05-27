package ru.pogosian.infrastructure.repository.JpaEntity.InStockCarOrder;

public enum InStockCarOrderStage {
    ApprovedByManager,
    AwaitingForPayment,
    Cancelled,
    Completed,
    ReadyForPickingUp,
    Payed,
    Placed
}