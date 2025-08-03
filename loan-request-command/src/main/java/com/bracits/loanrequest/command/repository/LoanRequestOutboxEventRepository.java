package com.bracits.loanrequest.command.repository;

import com.bracits.loanrequest.command.domain.outbox.LoanRequestOutboxEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestOutboxEventRepository extends MongoRepository<LoanRequestOutboxEvent, String> {
    // Basic CRUD operations - following the same pattern as loan-proposal service
}