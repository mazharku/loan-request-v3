package com.bracits.loanrequest.command.application.command;

public record RejectLoanRequestCommand(
        String tracerId,
        String proposalId,
        String rejectionReason,
        String rejectedBy
) {}