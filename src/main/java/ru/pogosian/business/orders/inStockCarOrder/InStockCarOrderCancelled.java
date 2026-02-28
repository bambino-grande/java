package ru.pogosian.business.orders.inStockCarOrder;

import ru.pogosian.business.excrptions.DomainValidationException;

public class InStockCarOrderCancelled implements InStockCarOrderStatusState {
    @Override
    public void next(InStockCarOrder order) {
        throw new DomainValidationException("order cancelled");
    }
}
