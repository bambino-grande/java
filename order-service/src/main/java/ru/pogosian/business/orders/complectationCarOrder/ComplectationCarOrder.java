package ru.pogosian.business.orders.complectationCarOrder;

import lombok.*;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrderStatusState;

import java.util.UUID;

@Getter
@Builder
public class ComplectationCarOrder {
    @Builder.Default
    private CompectationCarOrderStatusState state = new ComplectationCarOrderPlaced();
    private UUID orderId;
    private UUID clientId;
    private UUID managerId;
    private UUID carId;

    protected void setState(CompectationCarOrderStatusState state) {
        this.state = state;
    }

    public void nextState() {
        state.next(this);
    }
}