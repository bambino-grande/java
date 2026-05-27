package ru.pogosian.business.assembly;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AssemblyOrder {
    private UUID id;
    private UUID sourceOrderId;
    private AssemblyOrderStatus status;
    Instant createdAt;
    Instant updatedAt;
    boolean removed;

    public AssemblyOrder(UUID id, UUID sourceOrderId, AssemblyOrderStatus status) {
        this.id = id;
        this.sourceOrderId = sourceOrderId;
        this.status = status;
    }
}
