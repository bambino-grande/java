package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderPayed implements CompectationCarOrderStatusState{
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderAwaitingForShipping());
    }
}
