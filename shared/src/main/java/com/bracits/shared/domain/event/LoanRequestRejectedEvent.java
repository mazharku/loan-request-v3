package com.bracits.shared.domain.event;

import lombok.Getter;

@Getter
public class LoanRequestRejectedEvent extends DomainEvent {
    
    private final String proposalId;
    private final String rejectionReason;
    
    public LoanRequestRejectedEvent(String aggregateId, String proposalId,
                                    String reason) {
        super(aggregateId);
        this.proposalId = proposalId;
        this.rejectionReason = reason;
    }
}