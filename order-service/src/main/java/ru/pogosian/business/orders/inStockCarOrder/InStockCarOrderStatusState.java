package ru.pogosian.business.orders.inStockCarOrder;

public interface InStockCarOrderStatusState {
    void next (InStockCarOrder order);
}