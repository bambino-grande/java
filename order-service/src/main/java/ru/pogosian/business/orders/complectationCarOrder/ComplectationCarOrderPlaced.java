package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderPlaced implements CompectationCarOrderStatusState{
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderApprovedByWarehouseState());
    }
    public void canceled(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderCancelled());
    }
}
