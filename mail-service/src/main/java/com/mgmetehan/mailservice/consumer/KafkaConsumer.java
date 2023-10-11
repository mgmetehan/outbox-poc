package com.mgmetehan.mailservice.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgmetehan.mailservice.model.KafkaPayload;
import com.mgmetehan.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final MailService mailService;

    private static final String TOPIC_NAME = "account-created";
    private static final String GROUP_ID = "GroupId";
    private static final String Container_Factory = "ContainerFactory";

    private final ObjectMapper MAPPER = new ObjectMapper();

    @KafkaListener(topics = {TOPIC_NAME}, groupId = GROUP_ID, containerFactory = Container_Factory)
    public void listener(@Payload Object event, ConsumerRecord c) throws Exception {
        String value = (String) c.value();
        JsonNode payload = MAPPER.readTree(value);
        log.info("JSON NODE: {}", payload);
        KafkaPayload kafkaPayload = MAPPER.readValue(payload.get("payload").asText(), KafkaPayload.class);
        log.info("KafkaPayload: {}", kafkaPayload);
        log.info("KafkaPayload getId: {}", kafkaPayload.getId());
        mailService.sendMail(kafkaPayload.getUsername());
        mailService.deleteProcessByIdFromOutbox(kafkaPayload.getId());
    }
}
