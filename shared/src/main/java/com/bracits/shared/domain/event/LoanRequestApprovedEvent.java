package com.bracits.shared.domain.event;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LoanRequestApprovedEvent extends DomainEvent {
    
    private final String memberId;
    private final String proposalId;
    private final Double approvedLoanAmount;
    private final String approverId;
    private final String remarks;
    
    public LoanRequestApprovedEvent(String aggregateId, String memberId, String proposalId,
                                    Double approvedLoanAmount, String approverId, String remarks) {
        super(aggregateId);
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.approvedLoanAmount = approvedLoanAmount;
        this.approverId = approverId;
        this.remarks = remarks;
    }
}