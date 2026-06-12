package ru.pogosian.business.orders.inStockCarOrder;

public class InStockCarOrderIsReadyForPickingUp implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        order.setState(new InStockCarOrderCompleted());
    }
}
