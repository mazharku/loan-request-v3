package com.bracits.shared.infrastructure.messaging;

import com.bracits.shared.constants.ApplicationConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange loanRequestFanoutExchange() {
        return new FanoutExchange(ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE, true, false);
    }
    

    @Bean
    public Queue loanRequestCreatedQueue() {
        return QueueBuilder.durable(ApplicationConstants.LOAN_REQUEST_CREATED_QUEUE).build();
    }
    
    @Bean
    public Queue loanRequestApprovedQueue() {
        return QueueBuilder.durable(ApplicationConstants.LOAN_REQUEST_APPROVED_QUEUE).build();
    }
    
    @Bean
    public Queue loanRequestRejectedQueue() {
        return QueueBuilder.durable(ApplicationConstants.LOAN_REQUEST_REJECTED_QUEUE).build();
    }
    
    @Bean
    public Queue loanRequestUpdateQueue() {
        return QueueBuilder.durable(ApplicationConstants.LOAN_REQUEST_UPDATE_QUEUE).build();
    }
    
    
    // Fanout Bindings for Domain Events
    @Bean
    public Binding loanRequestCreatedFanoutBinding() {
        return BindingBuilder
                .bind(loanRequestCreatedQueue())
                .to(loanRequestFanoutExchange());
    }
    
    @Bean
    public Binding loanRequestApprovedFanoutBinding() {
        return BindingBuilder
                .bind(loanRequestApprovedQueue())
                .to(loanRequestFanoutExchange());
    }
    
    @Bean
    public Binding loanRequestRejectedFanoutBinding() {
        return BindingBuilder
                .bind(loanRequestRejectedQueue())
                .to(loanRequestFanoutExchange());
    }
    
    @Bean
    public Binding loanRequestUpdateFanoutBinding() {
        return BindingBuilder
                .bind(loanRequestUpdateQueue())
                .to(loanRequestFanoutExchange());
    }


    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }
    

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}