package com.bracits.loanrequest.command.application.command;

public record RejectLoanRequestCommand(
    String proposalId,
    String rejectionReason
) {}