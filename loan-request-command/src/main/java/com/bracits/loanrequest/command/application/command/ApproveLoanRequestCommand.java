package com.bracits.loanrequest.command.application.command;

public record ApproveLoanRequestCommand(
    String proposalId,
    Double approverId,
    double approvedLoanAmount,
    int approvedDurationInMonths
) {}