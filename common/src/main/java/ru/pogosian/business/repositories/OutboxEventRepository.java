package ru.pogosian.business.repositories;

import ru.pogosian.business.outbox.OutboxEvent;

import java.util.List;

public interface OutboxEventRepository {
    List<OutboxEvent> findPendingForUpdate();
    void save(OutboxEvent event);
    void delete(OutboxEvent event);
}
