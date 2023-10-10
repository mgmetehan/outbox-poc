package com.mgmetehan.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgmetehan.accountservice.model.Outbox;
import com.mgmetehan.accountservice.publisher.KafkaPublisher;
import com.mgmetehan.accountservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper MAPPER = new ObjectMapper();

    public Outbox createOutbox(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

    public void debeziumDatabaseChange(Map<String, Object> payload) {
        log.info("Debezium payload: {}", payload);
        try {
            kafkaPublisher.publish("account-created", MAPPER.writeValueAsString(payload));
            var x = MAPPER.writeValueAsString(payload);
            log.info("Debezium payload string: {}", x);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Outbox> findAll() {
        return outboxRepository.findAll();
    }

    public void deleteById(String id) {
        outboxRepository.deleteById(id);
    }
}
