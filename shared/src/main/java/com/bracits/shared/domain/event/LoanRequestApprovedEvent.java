package com.bracits.shared.domain.event;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LoanRequestApprovedEvent extends DomainEvent {
    
    private final String tracerId;
    private final String memberId;
    private final String proposalId;
    private final String proposalNo;
    private final String loanProductId;
    private final String officeId;
    private final String projectId;
    private final Double approvedLoanAmount;
    private final Double approvedGrantAmount;
    private final Integer approvedDurationInMonths;
    private final String approverId;
    private final String approvedBy;
    private final String remarks;
    private final LocalDateTime processedAt;
    
    public LoanRequestApprovedEvent(String aggregateId, String tracerId, String memberId,
                                    String proposalId, String proposalNo, String loanProductId,
                                    String officeId, String projectId, Double approvedLoanAmount,
                                    Double approvedGrantAmount, Integer approvedDurationInMonths,
                                    String approverId, String approvedBy, String remarks,
                                    LocalDateTime processedAt) {
        super(aggregateId);
        this.tracerId = tracerId;
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.proposalNo = proposalNo;
        this.loanProductId = loanProductId;
        this.officeId = officeId;
        this.projectId = projectId;
        this.approvedLoanAmount = approvedLoanAmount;
        this.approvedGrantAmount = approvedGrantAmount;
        this.approvedDurationInMonths = approvedDurationInMonths;
        this.approverId = approverId;
        this.approvedBy = approvedBy;
        this.remarks = remarks;
        this.processedAt = processedAt;
    }
}