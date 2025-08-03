package com.bracits.shared.domain.event;

import lombok.Getter;

@Getter
public class LoanRequestRejectedEvent extends DomainEvent {
    
    private final String memberId;
    private final String proposalId;
    private final String rejectedBy;
    private final String reason;
    
    public LoanRequestRejectedEvent(String aggregateId, String memberId, String proposalId,
                                    String rejectedBy, String reason) {
        super(aggregateId);
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.rejectedBy = rejectedBy;
        this.reason = reason;
    }
}