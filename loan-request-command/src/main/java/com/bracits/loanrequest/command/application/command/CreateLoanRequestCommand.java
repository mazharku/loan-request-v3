package com.bracits.loanrequest.command.application.command;

public record CreateLoanRequestCommand(
    String proposalId,
    String memberId,
    String loanProductId,
    double proposedLoanAmount,
    double interestRate,
    int noOfInstallments
) {}