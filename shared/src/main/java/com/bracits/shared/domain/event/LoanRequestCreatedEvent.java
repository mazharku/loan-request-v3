package com.bracits.shared.domain.event;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LoanRequestCreatedEvent extends DomainEvent {
    
    private final String tracerId;
    private final String memberId;
    private final String proposalNo;
    private final String proposalRefNo;
    private final String proposedCOId;
    private final String loanProductId;
    private final String loanSchemeId;
    private final String officeId;
    private final String projectId;
    private final LocalDateTime applicationDate;
    private final Double proposedLoanAmount;
    private final Double proposedGrantAmount;
    private final String proposalRemarks;
    private final String scannedFileName;
    private final Long loanProductPolicyId;
    private final Long loanProductDetailsId;
    private final Integer proposedDurationInMonths;
    private final Double interestRate;
    private final Integer noOfInstallments;
    private final Double installmentAmount;
    private final Integer recordStatus;
    private final Long cohortMappingId;
    private final Long assetPurchaseId;
    private final String bufferId;
    private final Integer earner;
    private final String memberOwnIncome;
    private final String memberFamilyIncome;
    private final Integer loanUser;
    private final Integer apiDataSourceId;
    private final Integer ageType;
    private final String createdBy;
    
    public LoanRequestCreatedEvent(String aggregateId, String tracerId, String memberId, String proposalNo,
                                   String proposalRefNo, String proposedCOId, String loanProductId,
                                   String loanSchemeId, String officeId, String projectId,
                                   LocalDateTime applicationDate, Double proposedLoanAmount,
                                   Double proposedGrantAmount, String proposalRemarks, String scannedFileName,
                                   Long loanProductPolicyId, Long loanProductDetailsId,
                                   Integer proposedDurationInMonths, Double interestRate,
                                   Integer noOfInstallments, Double installmentAmount, Integer recordStatus,
                                   Long cohortMappingId, Long assetPurchaseId, String bufferId,
                                   Integer earner, String memberOwnIncome, String memberFamilyIncome,
                                   Integer loanUser, Integer apiDataSourceId, Integer ageType,
                                   String createdBy) {
        super(aggregateId);
        this.tracerId = tracerId;
        this.memberId = memberId;
        this.proposalNo = proposalNo;
        this.proposalRefNo = proposalRefNo;
        this.proposedCOId = proposedCOId;
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
    }
}