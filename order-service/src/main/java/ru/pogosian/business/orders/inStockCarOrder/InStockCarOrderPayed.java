package ru.pogosian.business.orders.inStockCarOrder;

public class InStockCarOrderPayed implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        order.setState(new InStockCarOrderIsReadyForPickingUp());
    }
}
