package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderAwaitingForPaymen implements CompectationCarOrderStatusState{
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderPayed());
    }
    public void canceled(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderCancelled());
    }
}
