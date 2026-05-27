package ru.pogosian.infrastructure.repository.JpaEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import ru.pogosian.business.outbox.OutboxStatus;

import java.util.UUID;

@Getter
@Entity
@Table(name = "outbox_events")
@SQLRestriction("removed = false")
@NoArgsConstructor
public class OutboxEventJpaEntity extends BaseJpaEntity {
    @Column(nullable = false)
    private UUID aggregateId;

    @Column(nullable = false)
    private String routingKey;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(nullable = false)
    private UUID traceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus outboxStatus;

    public OutboxEventJpaEntity(UUID id, UUID aggregateId, String routingKey, String payload, UUID traceId,  OutboxStatus outboxStatus) {
        super(id);
        this.aggregateId = aggregateId;
        this.routingKey = routingKey;
        this.message = payload;
        this.traceId = traceId;
        this.outboxStatus = outboxStatus;
    }
}
