package ru.pogosian.business.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OutboxEvent {
    private final UUID id;
    private final UUID aggregateId;
    private final String routingKey;
    private final String message;
    private final UUID traceId;
    private OutboxStatus outboxStatus;

    public void makeSend(){
        this.outboxStatus = OutboxStatus.SENT;
    }
}
