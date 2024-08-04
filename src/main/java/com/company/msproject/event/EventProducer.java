package com.company.msproject.event;

import com.company.commoneventlib.event.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public void produce(ProductEvent event, String topic) {
        kafkaTemplate.send(topic, event.getId().toString(), event);
        log.info("Event sent");
    }
}
