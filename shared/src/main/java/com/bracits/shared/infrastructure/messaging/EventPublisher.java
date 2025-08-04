package com.bracits.shared.infrastructure.messaging;

import com.bracits.shared.domain.event.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    public void publishEvent(String exchange, String routingKey, DomainEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            MessageProperties properties = new MessageProperties();
            properties.setHeader("eventType", event.getClass().getSimpleName());
            Message message = new Message(eventJson.getBytes(), properties);

            rabbitTemplate.send(exchange, routingKey, message);
            //rabbitTemplate.convertAndSend(exchange, routingKey, eventJson);
            log.info("Published event: {} to exchange: {} with routing key: {}", 
                    event.getClass().getSimpleName(), exchange, routingKey);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event: {}", event.getClass().getSimpleName(), e);
            throw new RuntimeException("Failed to publish event", e);
        }
    }
    
    public void publishEventToFanout(String exchange, DomainEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            MessageProperties properties = new MessageProperties();
            properties.setHeader("eventType", event.getClass().getSimpleName());
            Message message = new Message(eventJson.getBytes(), properties);

            rabbitTemplate.send(exchange, "", message);
            //rabbitTemplate.convertAndSend(exchange, "", eventJson);
            log.info("Published event: {} to fanout exchange: {}", 
                    event.getClass().getSimpleName(), exchange);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event: {}", event.getClass().getSimpleName(), e);
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}