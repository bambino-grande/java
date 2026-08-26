package ru.pogosian.business.outbox;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OutboxEvent {
    private final UUID id;
    private final UUID aggregateId;
    private final String routingKey;
    private final String message;
    private final UUID traceId;
    private OutboxStatus outboxStatus;
    private int tryCount = 0;
    public void makeSend(){
        this.outboxStatus = OutboxStatus.SENT;
    }

    public OutboxEvent(UUID id,
                       UUID aggregateId,
                       String routingKey,
                       String message,
                       UUID traceId,
                       OutboxStatus outboxStatus,
                       int tryCount
    ){
        this.id = id;
        this.aggregateId = aggregateId;
        this.routingKey = routingKey;
        this.message = message;
        this.traceId = traceId;
        this.outboxStatus = outboxStatus;
        this.tryCount = tryCount;
    }

    public OutboxEvent(UUID id,
                       UUID aggregateId,
                       String routingKey,
                       String message,
                       UUID traceId,
                       OutboxStatus outboxStatus
    ){
        this.id = id;
        this.aggregateId = aggregateId;
        this.routingKey = routingKey;
        this.message = message;
        this.traceId = traceId;
        this.outboxStatus = outboxStatus;
        this.tryCount = 0;
    }


    public void increaseTryCount(){
        this.tryCount++;
    }
}
