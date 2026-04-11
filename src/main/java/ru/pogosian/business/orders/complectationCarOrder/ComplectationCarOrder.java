package ru.pogosian.business.orders.complectationCarOrder;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class ComplectationCarOrder {
    @Setter
    @Builder.Default
    private CompectationCarOrderStatusState state = new ComplectationCarOrderPlaced();
    private UUID orderId;
    private UUID clientId;
    private UUID managerId;
    private UUID carId;

    public void nextState() {
        state.next(this);
    }
}