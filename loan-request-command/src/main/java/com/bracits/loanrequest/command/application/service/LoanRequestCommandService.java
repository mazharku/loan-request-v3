package com.bracits.loanrequest.command.application.service;

import com.bracits.loanrequest.command.application.command.ApproveLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.CreateLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.RejectLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.UpdateLoanRequestCommand;
import com.bracits.shared.constants.ApplicationConstants;
import com.bracits.shared.domain.event.LoanRequestApprovedEvent;
import com.bracits.shared.domain.event.LoanRequestCreatedEvent;
import com.bracits.shared.domain.event.LoanRequestRejectedEvent;
import com.bracits.shared.domain.event.LoanRequestUpdatedEvent;
import com.bracits.shared.infrastructure.messaging.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanRequestCommandService {

    private final EventPublisher eventPublisher;

    public String createLoanRequest(CreateLoanRequestCommand command) {
        log.info("Publishing loan request created event for member: {}", command.memberId());
        
        LoanRequestCreatedEvent event = new LoanRequestCreatedEvent(
            command.aggregateId(),
            command.tracerId(),
            command.memberId(),
            command.proposalNo(),
            command.proposalRefNo(),
            command.proposedCOId(),
            command.loanProductId(),
            command.loanSchemeId(),
            command.officeId(),
            command.projectId(),
            command.applicationDate(),
            command.proposedLoanAmount(),
            command.proposedGrantAmount(),
            command.proposalRemarks(),
            command.scannedFileName(),
            command.loanProductPolicyId(),
            command.loanProductDetailsId(),
            command.proposedDurationInMonths(),
            command.interestRate(),
            command.noOfInstallments(),
            command.installmentAmount(),
            command.recordStatus(),
            command.cohortMappingId(),
            command.assetPurchaseId(),
            command.bufferId(),
            command.earner(),
            command.memberOwnIncome(),
            command.memberFamilyIncome(),
            command.loanUser(),
            command.apiDataSourceId(),
            command.ageType(),
            command.createdBy()
        );
        
        eventPublisher.publishEventToFanout(ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE, event);
        
        log.info("Loan request created event published for member: {}", command.memberId());
        return command.aggregateId();
    }

    public void approveLoanRequest(ApproveLoanRequestCommand command) {
        log.info("Publishing loan request approved event for proposal: {}", command.proposalId());
        
        LoanRequestApprovedEvent event = new LoanRequestApprovedEvent(
            command.proposalId(),
            command.tracerId(),
            null, // memberId - not available in approve command
            command.proposalId(),
            null, // proposalNo - not available in approve command
            null, // loanProductId - not available in approve command
            null, // officeId - not available in approve command
            null, // projectId - not available in approve command
            command.approvedLoanAmount(),
            command.approvedGrantAmount(),
            command.approvedDurationInMonths(),
            command.approverId(),
            command.approvedBy(),
            null, // remarks - not available in approve command
            null  // processedAt - not available in approve command
        );
        
        eventPublisher.publishEventToFanout(ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE, event);
        
        log.info("Loan request approved event published for proposal: {}", command.proposalId());
    }

    public void rejectLoanRequest(RejectLoanRequestCommand command) {
        log.info("Publishing loan request rejected event for proposal: {}", command.proposalId());
        
        LoanRequestRejectedEvent event = new LoanRequestRejectedEvent(
            command.proposalId(),
            command.tracerId(),
            null, // memberId - not available in reject command
            command.proposalId(),
            null, // proposalNo - not available in reject command
            null, // loanProductId - not available in reject command
            null, // officeId - not available in reject command
            null, // projectId - not available in reject command
            command.rejectionReason(),
            command.rejectedBy(),
            null  // processedAt - not available in reject command
        );
        
        eventPublisher.publishEventToFanout(ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE, event);
        
        log.info("Loan request rejected event published for proposal: {}", command.proposalId());
    }

    public void updateLoanRequest(UpdateLoanRequestCommand command) {
        log.info("Publishing loan request updated event for proposal: {}", command.proposalId());
        
        LoanRequestUpdatedEvent event = new LoanRequestUpdatedEvent(
            command.proposalId(),
            command.tracerId(),
            command.memberId(),
            command.proposalId(),
            command.proposalNo(),
            command.proposalRefNo(),
            command.proposedCOId(),
            command.loanProductId(),
            command.loanSchemeId(),
            command.officeId(),
            command.projectId(),
            command.applicationDate(),
            command.proposedLoanAmount(),
            command.proposedGrantAmount(),
            command.proposalRemarks(),
            command.scannedFileName(),
            command.loanProductPolicyId(),
            command.loanProductDetailsId(),
            command.proposedDurationInMonths(),
            command.interestRate(),
            command.noOfInstallments(),
            command.installmentAmount(),
            command.recordStatus(),
            command.cohortMappingId(),
            command.assetPurchaseId(),
            command.bufferId(),
            command.earner(),
            command.memberOwnIncome(),
            command.memberFamilyIncome(),
            command.loanUser(),
            command.apiDataSourceId(),
            command.ageType(),
            command.updatedBy()
        );
        
        eventPublisher.publishEventToFanout(ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE, event);
        
        log.info("Loan request updated event published for proposal: {}", command.proposalId());
    }
}