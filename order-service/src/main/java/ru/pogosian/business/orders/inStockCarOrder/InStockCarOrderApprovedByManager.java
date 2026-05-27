package ru.pogosian.business.orders.inStockCarOrder;

public class InStockCarOrderApprovedByManager implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        order.setState(new InStockCarOrderAwaitingForPaymen());
    }
    public void canceled(InStockCarOrder order) {
        order.setState(new InStockCarOrderCancelled());
    }
}
