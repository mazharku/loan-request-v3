package com.bracits.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoTransactionConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
        return new MongoTransactionManager(mongoTemplate.getMongoDatabaseFactory());
    }
}