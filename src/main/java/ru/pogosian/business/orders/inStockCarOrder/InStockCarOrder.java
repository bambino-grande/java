package ru.pogosian.business.orders.inStockCarOrder;

import lombok.*;
import java.util.UUID;

@Getter
@Builder
public class InStockCarOrder {
    @Setter
    private InStockCarOrderStatusState state = new InStockCarOrderPlaced();
    private UUID orderId;
    private UUID clientId;
    private UUID managerId;
    private UUID carId;

    public void nextState() {
        state.next(this);
    }
}