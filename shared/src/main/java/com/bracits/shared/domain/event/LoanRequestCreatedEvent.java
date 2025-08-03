package com.bracits.shared.domain.event;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LoanRequestCreatedEvent extends DomainEvent {
    
    private final String memberId;
    private final String proposalId;
    private final String loanProductId;
    private final Double proposedLoanAmount;
    private final Integer noOfInstallments;
    
    public LoanRequestCreatedEvent(String aggregateId, String memberId, String proposalId, 
                                   Double proposedLoanAmount, Integer noOfInstallments, String loanProductId) {
        super(aggregateId);
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.proposedLoanAmount = proposedLoanAmount;
        this.noOfInstallments = noOfInstallments;
        this.loanProductId = loanProductId;
    }
}