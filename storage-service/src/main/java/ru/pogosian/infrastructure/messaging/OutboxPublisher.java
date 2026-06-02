package ru.pogosian.infrastructure.messaging;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pogosian.business.outbox.OutboxEvent;
import ru.pogosian.business.repositories.OutboxEventRepository;
import ru.pogosian.config.RabbitMQConfig;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxEventRepository outboxEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelayString = "${outbox.publisher.fixed-delay-ms}")
    @Transactional
    public void publishPendingEvents() {
        for(OutboxEvent event : outboxEventRepository.findPendingForUpdate()){
            try (MDC.MDCCloseable ignored = MDC.putCloseable("traceId", event.getTraceId().toString())) {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, event.getRoutingKey(), event.getMessage());
                event.makeSend();
                outboxEventRepository.save(event);
                log.info("published out box event id:{} routingKey={}", event.getId(), event.getRoutingKey());
                }
            catch (Exception e) {
                log.error("publishing out box event failed", e);
                outboxEventRepository.delete(event);
            }
        }
    }
}
