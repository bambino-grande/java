package ru.pogosian.business.repositories;

import java.util.UUID;

public interface ProcessedEventRepository {
    boolean existsByEventId(UUID eventId);
    void save(UUID id);
}
