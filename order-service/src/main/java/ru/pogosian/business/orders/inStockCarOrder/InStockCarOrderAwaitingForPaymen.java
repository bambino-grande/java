package ru.pogosian.business.orders.inStockCarOrder;

public class InStockCarOrderAwaitingForPaymen implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        order.setState(new InStockCarOrderPayed());
    }
    public void canceled(InStockCarOrder order) {
        order.setState(new InStockCarOrderCancelled());
    }
}
