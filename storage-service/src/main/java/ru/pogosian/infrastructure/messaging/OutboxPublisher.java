package ru.pogosian.infrastructure.messaging;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.config.RabbitMQConfig;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxEventRepository outboxEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelayString = "${outbox.publisher.fixed-delay-ms}")
    @Transactional
    public void publishPendingEvents() {
        for(OutboxEvent event : outboxEventRepository.findPendingForUpdate()){
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, event.getRoutingKey(), event.getMessage());
            event.makeSend();
            outboxEventRepository.save(event);
        }
    }
}
