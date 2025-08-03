package com.bracits.loanrequest.command.repository;

import com.bracits.loanrequest.command.domain.event.LoanRequestEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRequestEventRepository extends MongoRepository<LoanRequestEvent, String> {
    
    @Query(value = "{ 'aggregate_identifier': ?0 }", sort = "{ 'timestamp': -1 }")
    Optional<LoanRequestEvent> findTopByAggregateIdentifierOrderByTimestampDesc(String aggregate_identifier);
    
    @Query(value = "{ 'aggregate_identifier': ?0 }", sort = "{ 'sequence_number': -1 }")
    Optional<LoanRequestEvent> findTopByAggregateIdentifierOrderBySequenceNumberDesc(String aggregate_identifier);
}