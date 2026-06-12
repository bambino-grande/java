package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderApprovedByWarehouseState implements CompectationCarOrderStatusState{
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderAwaitingForPaymen());
    }
    public void canceled(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderCancelled());
    }
}
