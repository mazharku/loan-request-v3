package com.bracits.loanrequest.command.application.command;

import java.time.LocalDateTime;

public record UpdateLoanRequestCommand(
        String tracerId,
        String proposalId,
        String proposalNo,
        String proposalRefNo,
        String proposedCOId,
        String memberId,
        String loanProductId,
        String loanSchemeId,
        String officeId,
        String projectId,
        LocalDateTime applicationDate,
        double proposedLoanAmount,
        double proposedGrantAmount,
        String proposalRemarks,
        String scannedFileName,
        long loanProductPolicyId,
        long loanProductDetailsId,
        int proposedDurationInMonths,
        double interestRate,
        int noOfInstallments,
        double installmentAmount,
        int recordStatus,
        Long cohortMappingId,
        Long assetPurchaseId,
        String bufferId,
        Integer earner,
        String memberOwnIncome,
        String memberFamilyIncome,
        Integer loanUser,
        Integer apiDataSourceId,
        Integer ageType,
        String updatedBy
) {}