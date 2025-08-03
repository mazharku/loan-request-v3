package com.bracits.loanrequest.command.domain.outbox;

import com.bracits.loanrequest.command.domain.LoanRequest;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "loan_request_outbox")
@Getter
@Setter
@NoArgsConstructor
public class LoanRequestOutboxEvent {
    
    @Id
    private String id;
    private String aggregateIdentifier;
    private String eventType;
    private LoanRequest payload;
    private Instant timestamp;
    private String status;
    
    public static LoanRequestOutboxEvent createCreatedEvent(LoanRequest loanRequest) {
        LoanRequestOutboxEvent event = new LoanRequestOutboxEvent();
        event.aggregateIdentifier = loanRequest.getId();
        event.eventType = "LoanRequestCreatedEvent";
        event.payload = loanRequest;
        event.timestamp = Instant.now();
        event.status = LoanRequestStatus.PENDING.name();
        return event;
    }
    
    public static LoanRequestOutboxEvent createApprovedEvent(LoanRequest loanRequest) {
        LoanRequestOutboxEvent event = new LoanRequestOutboxEvent();
        event.aggregateIdentifier = loanRequest.getId();
        event.eventType = "LoanRequestApprovedEvent";
        event.payload = loanRequest;
        event.timestamp = Instant.now();
        event.status = LoanRequestStatus.APPROVED.name();
        return event;
    }
    
    public static LoanRequestOutboxEvent createRejectedEvent(LoanRequest loanRequest) {
        LoanRequestOutboxEvent event = new LoanRequestOutboxEvent();
        event.aggregateIdentifier = loanRequest.getId();
        event.eventType = "LoanRequestRejectedEvent";
        event.payload = loanRequest;
        event.timestamp = Instant.now();
        event.status = LoanRequestStatus.REJECTED.name();
        return event;
    }
    
    public static LoanRequestOutboxEvent createUpdatedEvent(LoanRequest loanRequest) {
        LoanRequestOutboxEvent event = new LoanRequestOutboxEvent();
        event.aggregateIdentifier = loanRequest.getId();
        event.eventType = "LoanRequestUpdatedEvent";
        event.payload = loanRequest;
        event.timestamp = Instant.now();
        event.status = loanRequest.getStatus().name();
        return event;
    }
}