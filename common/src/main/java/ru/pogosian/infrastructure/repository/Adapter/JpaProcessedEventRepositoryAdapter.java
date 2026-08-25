package ru.pogosian.infrastructure.repository.Adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.repositories.ProcessedEventRepository;
import ru.pogosian.infrastructure.repository.JpaEntity.ProcessedEventJpaEntity;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaProcessedEventRepository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class JpaProcessedEventRepositoryAdapter implements ProcessedEventRepository {
    private final JpaProcessedEventRepository jpaProcessedEventRepository;
    @Override
    public boolean existsByEventId(UUID eventId) {
        return jpaProcessedEventRepository.existsById(eventId);
    }

    @Override
    public void save(UUID id) {
        jpaProcessedEventRepository.save(new ProcessedEventJpaEntity(id));
    }
}