package ru.pogosian.business.orders.inStockCarOrder;

import lombok.*;
import java.util.UUID;

@Getter
@Builder
public class InStockCarOrder {
    @Builder.Default
    private InStockCarOrderStatusState state = new InStockCarOrderPlaced();
    private UUID orderId;
    private UUID clientId;
    private UUID managerId;
    private UUID carId;

    protected void setState(InStockCarOrderStatusState state) {
        this.state = state;
    }

    public void nextState() {
        state.next(this);
    }
}