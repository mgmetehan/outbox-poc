package com.mgmetehan.accountservice.consumer;

import com.mgmetehan.accountservice.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final OutboxService outboxService;
    private static final String TOPIC_NAME = "delete-process-byId-from-outbox";
    private static final String GROUP_ID = "GroupId";
    private static final String Container_Factory = "ContainerFactory";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID, containerFactory = Container_Factory)
    public void consumeMessage(String message) {
        log.info("Received message: " + message);
        outboxService.deleteById(message);
    }
}
