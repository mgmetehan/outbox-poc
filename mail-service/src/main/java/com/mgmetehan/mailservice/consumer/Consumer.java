package com.mgmetehan.mailservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Consumer {

    private static final String TOPIC_NAME = "account-created";
    private static final String GROUP_ID = "KafkaConsumer-GroupId";
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {TOPIC_NAME}, groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload Object event, ConsumerRecord c) throws Exception {
        log.info("PAYLOAD: {}", event);
    }
}
