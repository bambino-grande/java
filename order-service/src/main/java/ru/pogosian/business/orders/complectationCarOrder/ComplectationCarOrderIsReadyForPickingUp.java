package ru.pogosian.business.orders.complectationCarOrder;

public class ComplectationCarOrderIsReadyForPickingUp implements CompectationCarOrderStatusState {
    @Override
    public void next(ComplectationCarOrder order) {
        order.setState(new ComplectationCarOrderCompleted());
    }
}
