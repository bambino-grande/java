package ru.pogosian.business.orders.inStockCarOrder;

public class InStockCarOrderPlaced implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        order.setState(new InStockCarOrderApprovedByManager());
    }
    public void canceled(InStockCarOrder order) {
        order.setState(new InStockCarOrderCancelled());
    }
}
