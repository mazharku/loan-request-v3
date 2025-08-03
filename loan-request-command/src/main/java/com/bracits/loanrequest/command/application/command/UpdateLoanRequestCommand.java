package com.bracits.loanrequest.command.application.command;

public record UpdateLoanRequestCommand(
    String proposalId,
    String memberId,
    String loanProductId,
    double proposedLoanAmount,
    double interestRate,
    int noOfInstallments
) {}