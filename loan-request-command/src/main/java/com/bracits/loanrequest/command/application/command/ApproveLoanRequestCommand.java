package com.bracits.loanrequest.command.application.command;

public record ApproveLoanRequestCommand(
        String tracerId,
        String proposalId,
        String approvedBy,
        String approverId,
        double approvedLoanAmount,
        double approvedGrantAmount,
        int approvedDurationInMonths
) {}