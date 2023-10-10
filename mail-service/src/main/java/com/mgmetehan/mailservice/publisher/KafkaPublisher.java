package com.mgmetehan.mailservice.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(String topicName, Object message) {
        kafkaTemplate.send(topicName, message);
    }
}
