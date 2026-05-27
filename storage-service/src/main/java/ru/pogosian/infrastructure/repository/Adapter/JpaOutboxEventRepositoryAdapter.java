package ru.pogosian.infrastructure.repository.Adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.outbox.OutboxStatus;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.infrastructure.repository.JpaRepositories.JpaOutboxEventRepository;
import ru.pogosian.infrastructure.repository.Mapper.OutboxEventMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class JpaOutboxEventRepositoryAdapter implements OutboxEventRepository {
    private final JpaOutboxEventRepository jpaOutboxEventRepository;
    private final OutboxEventMapper outboxEventMapper;
    @Override
    public List<OutboxEvent> findPendingForUpdate() {
        return jpaOutboxEventRepository.findByOutboxStatus(OutboxStatus.PENDING)
                .stream()
                .map(outboxEventMapper::toDomain)
                .toList();
    }

    @Override
    public void save(OutboxEvent event) {
        jpaOutboxEventRepository.save(outboxEventMapper.toJpaEntity(event));
    }
}
