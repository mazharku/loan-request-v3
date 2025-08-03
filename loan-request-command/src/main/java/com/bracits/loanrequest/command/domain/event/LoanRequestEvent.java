package com.bracits.loanrequest.command.domain.event;

import com.bracits.loanrequest.command.domain.LoanRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "loan_request_events")
@Getter
@Setter
@NoArgsConstructor
public class LoanRequestEvent {
    
    @Id
    private String id;
    private String aggregate_identifier;
    private String event_identifier;
    private String type;
    private Instant timestamp;
    private String payload_type;
    private String payload_revision;
    private LoanRequest payload;
    
    public static LoanRequestEvent createCreatedEvent(LoanRequest loanRequest) {
        LoanRequestEvent event = new LoanRequestEvent();
        event.aggregate_identifier = loanRequest.getId();
        event.event_identifier = java.util.UUID.randomUUID().toString();
        event.type = "LoanRequestCreatedEvent";
        event.timestamp = Instant.now();
        event.payload_type = "LoanRequest";
        event.payload_revision = "1.0";
        event.payload = loanRequest;
        return event;
    }
    
    public static LoanRequestEvent createApprovedEvent(LoanRequest loanRequest) {
        LoanRequestEvent event = new LoanRequestEvent();
        event.aggregate_identifier = loanRequest.getId();
        event.event_identifier = java.util.UUID.randomUUID().toString();
        event.type = "LoanRequestApprovedEvent";
        event.timestamp = Instant.now();
        event.payload_type = "LoanRequest";
        event.payload_revision = "1.0";
        event.payload = loanRequest;
        return event;
    }
    
    public static LoanRequestEvent createRejectedEvent(LoanRequest loanRequest) {
        LoanRequestEvent event = new LoanRequestEvent();
        event.aggregate_identifier = loanRequest.getId();
        event.event_identifier = java.util.UUID.randomUUID().toString();
        event.type = "LoanRequestRejectedEvent";
        event.timestamp = Instant.now();
        event.payload_type = "LoanRequest";
        event.payload_revision = "1.0";
        event.payload = loanRequest;
        return event;
    }
    
    public static LoanRequestEvent createUpdatedEvent(LoanRequest loanRequest) {
        LoanRequestEvent event = new LoanRequestEvent();
        event.aggregate_identifier = loanRequest.getId();
        event.event_identifier = java.util.UUID.randomUUID().toString();
        event.type = "LoanRequestUpdatedEvent";
        event.timestamp = Instant.now();
        event.payload_type = "LoanRequest";
        event.payload_revision = "1.0";
        event.payload = loanRequest;
        return event;
    }
}