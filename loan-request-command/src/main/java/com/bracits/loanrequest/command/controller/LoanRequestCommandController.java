package com.bracits.loanrequest.command.controller;

import com.bracits.loanrequest.command.application.command.ApproveLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.CreateLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.RejectLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.UpdateLoanRequestCommand;
import com.bracits.loanrequest.command.application.service.LoanRequestCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-requests")
@RequiredArgsConstructor
public class LoanRequestCommandController {
    
    private final LoanRequestCommandService commandService;
    
    @GetMapping("/health")
    public String health() {
        return "Loan Request Command Service is running";
    }
    
    @PostMapping
    public ResponseEntity<String> createLoanRequest(@RequestBody CreateLoanRequestCommand command) {
        String loanRequestId = commandService.createLoanRequest(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanRequestId);
    }
    
    @PutMapping("/proposal/{proposalId}/approve")
    public ResponseEntity<Void> approveLoanRequest(
            @PathVariable String proposalId,
            @RequestBody ApproveLoanRequestCommand command) {
        ApproveLoanRequestCommand commandWithId = new ApproveLoanRequestCommand(
            proposalId,
            command.approverId(),
            command.approvedLoanAmount(),
            command.approvedDurationInMonths()
        );
        commandService.approveLoanRequest(commandWithId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/proposal/{proposalId}/reject")
    public ResponseEntity<Void> rejectLoanRequest(
            @PathVariable String proposalId,
            @RequestBody RejectLoanRequestCommand command) {
        RejectLoanRequestCommand commandWithId = new RejectLoanRequestCommand(
            proposalId,
            command.rejectionReason()
        );
        commandService.rejectLoanRequest(commandWithId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/proposal/{proposalId}")
    public ResponseEntity<Void> updateLoanRequest(
            @PathVariable String proposalId,
            @RequestBody UpdateLoanRequestCommand command) {

        UpdateLoanRequestCommand commandWithId = new UpdateLoanRequestCommand(
            proposalId,
            command.memberId(),
            command.loanProductId(),
            command.proposedLoanAmount(),
            command.interestRate(),
            command.noOfInstallments()
        );
        commandService.updateLoanRequest(commandWithId);
        return ResponseEntity.ok().build();
    }
}