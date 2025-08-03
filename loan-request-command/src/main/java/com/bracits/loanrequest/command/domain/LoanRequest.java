package com.bracits.loanrequest.command.domain;

import com.bracits.shared.domain.aggregate.AggregateRoot;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import com.bracits.shared.domain.event.LoanRequestApprovedEvent;
import com.bracits.shared.domain.event.LoanRequestCreatedEvent;
import com.bracits.shared.domain.event.LoanRequestRejectedEvent;
import com.bracits.shared.domain.event.LoanRequestUpdatedEvent;
import com.bracits.shared.exception.LoanRequestBusinessException;
import com.bracits.shared.exception.LoanRequestValidationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "loan_requests")
@Getter
@NoArgsConstructor
public class LoanRequest extends AggregateRoot {
    
    private String memberId;
    private String proposalId;
    private String loanProductId;
    private Double proposedLoanAmount;
    private Double approvedLoanAmount;
    private Double interestRate;
    private Integer noOfInstallments;
    private Integer approvedDurationInMonths;
    private LoanRequestStatus status;
    private String remarks;
    private String processedBy;
    private LocalDateTime processedAt;
    
    public LoanRequest(String memberId, String proposalId, Double proposedLoanAmount, Double interestRate,
                       Integer noOfInstallments, String loanProductId) {
        super();
        validateCreationParams(memberId, proposalId, proposedLoanAmount, noOfInstallments, loanProductId);
        
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.proposedLoanAmount = proposedLoanAmount;
        this.noOfInstallments = noOfInstallments;
        this.loanProductId = loanProductId;
        this.interestRate = interestRate;
        this.status = LoanRequestStatus.PENDING;

        registerEvent(new LoanRequestCreatedEvent(
            this.id,
            this.memberId,
            this.proposalId,
            this.proposedLoanAmount,
            this.noOfInstallments,
            this.loanProductId
        ));
    }
    
    public void approve(Double approvedLoanAmount, Integer approvedDurationInMonths, String approverId) {
        if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only approve pending loan requests. Current status: " + this.status);
        }
        validateApprovalParams(approvedLoanAmount, approverId);
        
        this.approvedLoanAmount = approvedLoanAmount;
        this.approvedDurationInMonths = approvedDurationInMonths;
        this.status = LoanRequestStatus.APPROVED;
        this.processedBy = approverId;
        this.processedAt = LocalDateTime.now();
        updateTimestamp();

        registerEvent(new LoanRequestApprovedEvent(
            this.id,
            this.memberId,
            this.proposalId,
            this.approvedLoanAmount,
            this.processedBy,
            this.remarks
        ));
    }
    
    public void reject(String rejectedBy, String rejectionReason) {
        if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only reject pending loan requests. Current status: " + this.status);
        }
        validateRejectionParams(rejectionReason);
        
        this.status = LoanRequestStatus.REJECTED;
        this.processedBy = rejectedBy;
        this.processedAt = LocalDateTime.now();
        this.remarks = rejectionReason;
        updateTimestamp();
        

        registerEvent(new LoanRequestRejectedEvent(
            this.id,
            this.memberId,
            this.proposalId,
            this.processedBy,
            this.remarks
        ));
    }
    
    public void update(String memberId, String proposalId, Double proposedLoanAmount, 
                      Integer noOfInstallments, String loanProductId) {
        if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only update pending loan requests. Current status: " + this.status);
        }
        validateCreationParams(memberId, proposalId, proposedLoanAmount, noOfInstallments, loanProductId);
        
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.proposedLoanAmount = proposedLoanAmount;
        this.noOfInstallments = noOfInstallments;
        this.loanProductId = loanProductId;
        updateTimestamp();

        registerEvent(new LoanRequestUpdatedEvent(
            this.id,
            this.memberId,
            this.proposalId,
            this.proposedLoanAmount,
            this.noOfInstallments,
            this.loanProductId
        ));
    }
    
    private void validateCreationParams(String memberId, String proposalId, Double proposedLoanAmount, 
                                       Integer noOfInstallments, String loanProductId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new LoanRequestValidationException("Member ID cannot be null or empty");
        }
        if (proposalId == null || proposalId.trim().isEmpty()) {
            throw new LoanRequestValidationException("Proposal ID cannot be null or empty");
        }
        if (proposedLoanAmount == null || proposedLoanAmount <= 0) {
            throw new LoanRequestValidationException("Proposed loan amount must be positive");
        }
        if (noOfInstallments == null || noOfInstallments <= 0) {
            throw new LoanRequestValidationException("Number of installments must be positive");
        }
        if (loanProductId == null || loanProductId.trim().isEmpty()) {
            throw new LoanRequestValidationException("Loan product ID cannot be null or empty");
        }
    }
    
    private void validateApprovalParams(Double approvedLoanAmount, String approverId) {
        if (approvedLoanAmount == null || approvedLoanAmount <= 0) {
            throw new LoanRequestValidationException("Approved loan amount must be positive");
        }
        if (approverId == null || approverId.trim().isEmpty()) {
            throw new LoanRequestValidationException("Approver ID cannot be null or empty");
        }
    }
    
    private void validateRejectionParams(String rejectionReason) {
        if (rejectionReason == null || rejectionReason.trim().isEmpty()) {
            throw new LoanRequestValidationException("Rejection reason cannot be null or empty");
        }
    }
}