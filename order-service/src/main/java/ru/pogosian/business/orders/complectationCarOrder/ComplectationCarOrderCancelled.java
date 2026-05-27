package ru.pogosian.business.orders.complectationCarOrder;

import ru.pogosian.business.excrptions.DomainValidationException;

public class ComplectationCarOrderCancelled implements CompectationCarOrderStatusState {
    @Override
    public void next(ComplectationCarOrder order) {
        throw new DomainValidationException("order cancelled");
    }
}
