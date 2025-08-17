package com.bracits.loanrequest.command.domain;

import com.bracits.shared.domain.aggregate.AggregateRoot;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import com.bracits.shared.domain.event.LoanRequestApprovedEvent;
import com.bracits.shared.domain.event.LoanRequestCreatedEvent;
import com.bracits.shared.domain.event.LoanRequestRejectedEvent;
import com.bracits.shared.domain.event.LoanRequestUpdatedEvent;
import com.bracits.shared.exception.LoanRequestValidationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "loan_requests")
@Getter
@NoArgsConstructor
public class LoanRequest extends AggregateRoot {
    
    private String aggregateId;
    private String tracerId;
    private String memberId;
    private String proposalId;
    private String proposalNo;
    private String proposalRefNo;
    private String proposedCOId;
    private String loanProductId;
    private String loanSchemeId;
    private String officeId;
    private String projectId;
    private LocalDateTime applicationDate;
    private Double proposedLoanAmount;
    private Double proposedGrantAmount;
    private Double approvedLoanAmount;
    private Double interestRate;
    private Integer noOfInstallments;
    private Integer proposedDurationInMonths;
    private Double installmentAmount;
    private Integer approvedDurationInMonths;
    private LoanRequestStatus status;
    private String remarks;
    private String proposalRemarks;
    private String scannedFileName;
    private Long loanProductPolicyId;
    private Long loanProductDetailsId;
    private Integer recordStatus;
    private Long cohortMappingId;
    private Long assetPurchaseId;
    private String bufferId;
    private Integer earner;
    private String memberOwnIncome;
    private String memberFamilyIncome;
    private Integer loanUser;
    private Integer apiDataSourceId;
    private Integer ageType;
    private String approverId;
    private String createdBy;
    private LocalDateTime processedAt;
    
    public LoanRequest(String aggregateId, String tracerId, String proposalNo, String proposalRefNo,
                       String proposedCOId, String memberId, String loanProductId, String loanSchemeId,
                       String officeId, String projectId, LocalDateTime applicationDate,
                       Double proposedLoanAmount, Double proposedGrantAmount, String proposalRemarks,
                       String scannedFileName, Long loanProductPolicyId, Long loanProductDetailsId,
                       Integer proposedDurationInMonths, Double interestRate, Integer noOfInstallments,
                       Double installmentAmount, Integer recordStatus, Long cohortMappingId,
                       Long assetPurchaseId, String bufferId, Integer earner, String memberOwnIncome,
                       String memberFamilyIncome, Integer loanUser, Integer apiDataSourceId,
                       Integer ageType, String createdBy) {
        super();
        this.aggregateId = aggregateId;
        this.tracerId = tracerId;
        this.proposalNo = proposalNo;
        this.proposalRefNo = proposalRefNo;
        this.proposedCOId = proposedCOId;
        this.memberId = memberId;
        this.loanProductId = loanProductId;
        this.loanSchemeId = loanSchemeId;
        this.officeId = officeId;
        this.projectId = projectId;
        this.applicationDate = applicationDate;
        this.proposedLoanAmount = proposedLoanAmount;
        this.proposedGrantAmount = proposedGrantAmount;
        this.proposalRemarks = proposalRemarks;
        this.scannedFileName = scannedFileName;
        this.loanProductPolicyId = loanProductPolicyId;
        this.loanProductDetailsId = loanProductDetailsId;
        this.proposedDurationInMonths = proposedDurationInMonths;
        this.interestRate = interestRate;
        this.noOfInstallments = noOfInstallments;
        this.installmentAmount = installmentAmount;
        this.recordStatus = recordStatus;
        this.cohortMappingId = cohortMappingId;
        this.assetPurchaseId = assetPurchaseId;
        this.bufferId = bufferId;
        this.earner = earner;
        this.memberOwnIncome = memberOwnIncome;
        this.memberFamilyIncome = memberFamilyIncome;
        this.loanUser = loanUser;
        this.apiDataSourceId = apiDataSourceId;
        this.ageType = ageType;
        this.createdBy = createdBy;
        this.status = LoanRequestStatus.PENDING;

        registerEvent(new LoanRequestCreatedEvent(
            this.id,
            this.tracerId,
            this.memberId,
            this.proposalNo,
            this.proposalRefNo,
            this.proposedCOId,
            this.loanProductId,
            this.loanSchemeId,
            this.officeId,
            this.projectId,
            this.applicationDate,
            this.proposedLoanAmount,
            this.proposedGrantAmount,
            this.proposalRemarks,
            this.scannedFileName,
            this.loanProductPolicyId,
            this.loanProductDetailsId,
            this.proposedDurationInMonths,
            this.interestRate,
            this.noOfInstallments,
            this.installmentAmount,
            this.recordStatus,
            this.cohortMappingId,
            this.assetPurchaseId,
            this.bufferId,
            this.earner,
            this.memberOwnIncome,
            this.memberFamilyIncome,
            this.loanUser,
            this.apiDataSourceId,
            this.ageType,
            this.createdBy
        ));
    }
    
    public void approve(String proposalId,Double approvedLoanAmount, Integer approvedDurationInMonths, String approverId) {
       /* if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only approve pending loan requests. Current status: " + this.status);
        }*/
        //validateApprovalParams(approvedLoanAmount, approverId);

        this.proposalId= proposalId;
        this.approvedLoanAmount = approvedLoanAmount;
        this.approvedDurationInMonths = approvedDurationInMonths;
        this.status = LoanRequestStatus.APPROVED;
        this.approverId = approverId;
        this.processedAt = LocalDateTime.now();
        updateTimestamp();

        registerEvent(new LoanRequestApprovedEvent(
            this.id,
            this.tracerId,
            this.memberId,
            this.proposalId,
            this.proposalNo,
            this.loanProductId,
            this.officeId,
            this.projectId,
            this.approvedLoanAmount,
            this.proposedGrantAmount,
            this.approvedDurationInMonths,
            this.approverId,
            null,
            this.remarks,
            this.processedAt
        ));
    }
    
    public void reject(String proposalId,String rejectionReason) {
        /*if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only reject pending loan requests. Current status: " + this.status);
        }
        validateRejectionParams(rejectionReason);*/
        this.proposalId= proposalId;
        this.status = LoanRequestStatus.REJECTED;
        this.processedAt = LocalDateTime.now();
        this.remarks = rejectionReason;
        updateTimestamp();
        

        registerEvent(new LoanRequestRejectedEvent(
            this.id,
            this.tracerId,
            this.memberId,
            this.proposalId,
            this.proposalNo,
            this.loanProductId,
            this.officeId,
            this.projectId,
            this.remarks,
            null,
            this.processedAt
        ));
    }
    
    public void update(String tracerId, String proposalNo, String proposalRefNo, String proposedCOId,
                      String memberId, String loanProductId, String loanSchemeId, String officeId,
                      String projectId, LocalDateTime applicationDate, Double proposedLoanAmount,
                      Double proposedGrantAmount, String proposalRemarks, String scannedFileName,
                      Long loanProductPolicyId, Long loanProductDetailsId, Integer proposedDurationInMonths,
                      Double interestRate, Integer noOfInstallments, Double installmentAmount,
                      Integer recordStatus, Long cohortMappingId, Long assetPurchaseId, String bufferId,
                      Integer earner, String memberOwnIncome, String memberFamilyIncome, Integer loanUser,
                      Integer apiDataSourceId, Integer ageType, String updatedBy) {
        /*if (this.status != LoanRequestStatus.PENDING) {
            throw new LoanRequestBusinessException("Can only update pending loan requests. Current status: " + this.status);
        }
        validateCreationParams(memberId, proposalId, proposedLoanAmount, noOfInstallments, loanProductId);*/
        
        this.tracerId = tracerId;
        this.proposalNo = proposalNo;
        this.proposalRefNo = proposalRefNo;
        this.proposedCOId = proposedCOId;
        this.memberId = memberId;
        this.loanProductId = loanProductId;
        this.loanSchemeId = loanSchemeId;
        this.officeId = officeId;
        this.projectId = projectId;
        this.applicationDate = applicationDate;
        this.proposedLoanAmount = proposedLoanAmount;
        this.proposedGrantAmount = proposedGrantAmount;
        this.proposalRemarks = proposalRemarks;
        this.scannedFileName = scannedFileName;
        this.loanProductPolicyId = loanProductPolicyId;
        this.loanProductDetailsId = loanProductDetailsId;
        this.proposedDurationInMonths = proposedDurationInMonths;
        this.interestRate = interestRate;
        this.noOfInstallments = noOfInstallments;
        this.installmentAmount = installmentAmount;
        this.recordStatus = recordStatus;
        this.cohortMappingId = cohortMappingId;
        this.assetPurchaseId = assetPurchaseId;
        this.bufferId = bufferId;
        this.earner = earner;
        this.memberOwnIncome = memberOwnIncome;
        this.memberFamilyIncome = memberFamilyIncome;
        this.loanUser = loanUser;
        this.apiDataSourceId = apiDataSourceId;
        this.ageType = ageType;
        this.createdBy = updatedBy;
        updateTimestamp();

        registerEvent(new LoanRequestUpdatedEvent(
            this.id,
            this.tracerId,
            this.memberId,
            this.proposalId,
            this.proposalNo,
            this.proposalRefNo,
            this.proposedCOId,
            this.loanProductId,
            this.loanSchemeId,
            this.officeId,
            this.projectId,
            this.applicationDate,
            this.proposedLoanAmount,
            this.proposedGrantAmount,
            this.proposalRemarks,
            this.scannedFileName,
            this.loanProductPolicyId,
            this.loanProductDetailsId,
            this.proposedDurationInMonths,
            this.interestRate,
            this.noOfInstallments,
            this.installmentAmount,
            this.recordStatus,
            this.cohortMappingId,
            this.assetPurchaseId,
            this.bufferId,
            this.earner,
            this.memberOwnIncome,
            this.memberFamilyIncome,
            this.loanUser,
            this.apiDataSourceId,
            this.ageType,
            this.createdBy
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