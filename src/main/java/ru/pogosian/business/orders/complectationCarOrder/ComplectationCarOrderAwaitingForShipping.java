package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderAwaitingForShipping implements CompectationCarOrderStatusState{
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderIsReadyForPickingUp());
    }
}
