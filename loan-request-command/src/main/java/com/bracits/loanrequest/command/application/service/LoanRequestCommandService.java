package com.bracits.loanrequest.command.application.service;

import com.bracits.loanrequest.command.application.command.ApproveLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.CreateLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.RejectLoanRequestCommand;
import com.bracits.loanrequest.command.application.command.UpdateLoanRequestCommand;
import com.bracits.loanrequest.command.domain.LoanRequest;
import com.bracits.loanrequest.command.domain.event.LoanRequestEvent;
import com.bracits.loanrequest.command.domain.outbox.LoanRequestOutboxEvent;
import com.bracits.loanrequest.command.repository.LoanRequestEventRepository;
import com.bracits.loanrequest.command.repository.LoanRequestOutboxEventRepository;
import com.bracits.loanrequest.command.repository.LoanRequestRepository;
import com.bracits.shared.constants.ApplicationConstants;
import com.bracits.shared.domain.event.DomainEvent;
import com.bracits.shared.exception.LoanRequestNotFoundException;
import com.bracits.shared.exception.LoanRequestProcessingException;
import com.bracits.shared.infrastructure.messaging.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanRequestCommandService {
    
    private final LoanRequestRepository loanRequestRepository;
    private final LoanRequestEventRepository loanRequestEventRepository;
    private final LoanRequestOutboxEventRepository loanRequestOutboxEventRepository;
    private final EventPublisher eventPublisher;
    
    @Transactional
    public String createLoanRequest(CreateLoanRequestCommand command) {
        try {
            log.info("Creating loan request for member: {}", command.memberId());

            LoanRequest loanRequest = new LoanRequest(
                command.memberId(),
                command.proposalId(),
                command.proposedLoanAmount(),
                command.interestRate(),
                command.noOfInstallments(),
                command.loanProductId()
            );
            
            LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);
            
            // Save event sourcing event
            LoanRequestEvent loanRequestEvent = LoanRequestEvent.createCreatedEvent(savedLoanRequest);
            loanRequestEventRepository.save(loanRequestEvent);
            
            // Save outbox event
            LoanRequestOutboxEvent loanRequestOutboxEvent = LoanRequestOutboxEvent.createCreatedEvent(savedLoanRequest);
            loanRequestOutboxEventRepository.save(loanRequestOutboxEvent);
            
            // Publish domain events to fanout exchange
            for (DomainEvent event : savedLoanRequest.getUncommittedEvents()) {
                eventPublisher.publishEventToFanout(
                    ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE,
                    event
                );
            }
            savedLoanRequest.markEventsAsCommitted();
            
            log.info("Loan request created with ID: {}", savedLoanRequest.getId());
            return savedLoanRequest.getId();
        } catch (Exception ex) {
            log.error("Error creating loan request for member: {}", command.memberId(), ex);
            throw new LoanRequestProcessingException("Failed to create loan request", ex);
        }
    }
    
    @Transactional
    public void approveLoanRequest(ApproveLoanRequestCommand command) {
        log.info("Approving loan request for proposal: {}", command.proposalId());
        
        LoanRequest loanRequest = loanRequestRepository.findByProposalId(command.proposalId())
            .orElseThrow(() -> new LoanRequestNotFoundException(command.proposalId(), "proposal ID"));
        

        loanRequest.approve(
            command.approvedLoanAmount(),
            command.approvedDurationInMonths(),
            command.approverId().toString()
        );
        
        LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);
        
        // Save event sourcing event
        LoanRequestEvent loanRequestEvent = LoanRequestEvent.createApprovedEvent(savedLoanRequest);
        loanRequestEventRepository.save(loanRequestEvent);
        
        // Save outbox event
        LoanRequestOutboxEvent loanRequestOutboxEvent = LoanRequestOutboxEvent.createApprovedEvent(savedLoanRequest);
        loanRequestOutboxEventRepository.save(loanRequestOutboxEvent);
        
        // Publish domain events to fanout exchange
        for (DomainEvent event : savedLoanRequest.getUncommittedEvents()) {
            eventPublisher.publishEventToFanout(
                ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE,
                event
            );
        }
        savedLoanRequest.markEventsAsCommitted();
        
        log.info("Loan request approved for proposal: {}", command.proposalId());
    }
    
    @Transactional
    public void rejectLoanRequest(RejectLoanRequestCommand command) {
        log.info("Rejecting loan request for proposal: {}", command.proposalId());
        
        LoanRequest loanRequest = loanRequestRepository.findByProposalId(command.proposalId())
            .orElseThrow(() -> new LoanRequestNotFoundException(command.proposalId(), "proposal ID"));
        
        loanRequest.reject(null, command.rejectionReason()); // No rejectedBy field available
        
        LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);
        
        // Save event sourcing event
        LoanRequestEvent loanRequestEvent = LoanRequestEvent.createRejectedEvent(savedLoanRequest);
        loanRequestEventRepository.save(loanRequestEvent);
        
        // Save outbox event
        LoanRequestOutboxEvent loanRequestOutboxEvent = LoanRequestOutboxEvent.createRejectedEvent(savedLoanRequest);
        loanRequestOutboxEventRepository.save(loanRequestOutboxEvent);
        
        // Publish domain events to fanout exchange
        for (DomainEvent event : savedLoanRequest.getUncommittedEvents()) {
            eventPublisher.publishEventToFanout(
                ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE,
                event
            );
        }
        savedLoanRequest.markEventsAsCommitted();
        
        log.info("Loan request rejected for proposal: {}", command.proposalId());
    }
    
    @Transactional
    public void updateLoanRequest(UpdateLoanRequestCommand command) {
        log.info("Updating loan request for proposal: {}", command.proposalId());
        
        LoanRequest loanRequest = loanRequestRepository.findByProposalId(command.proposalId())
            .orElseThrow(() -> new LoanRequestNotFoundException(command.proposalId(), "proposal ID"));
        
        loanRequest.update(
            command.memberId(),
            command.proposalId(),
            command.proposedLoanAmount(),
            command.noOfInstallments(),
            command.loanProductId()
        );
        
        LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);
        
        // Save event sourcing event
        LoanRequestEvent loanRequestEvent = LoanRequestEvent.createUpdatedEvent(savedLoanRequest);
        loanRequestEventRepository.save(loanRequestEvent);
        
        // Save outbox event
        LoanRequestOutboxEvent loanRequestOutboxEvent = LoanRequestOutboxEvent.createUpdatedEvent(savedLoanRequest);
        loanRequestOutboxEventRepository.save(loanRequestOutboxEvent);
        
        // Publish domain events to fanout exchange
        for (DomainEvent event : savedLoanRequest.getUncommittedEvents()) {
            eventPublisher.publishEventToFanout(
                ApplicationConstants.LOAN_REQUEST_FANOUT_EXCHANGE,
                event
            );
        }
        savedLoanRequest.markEventsAsCommitted();
        
        log.info("Loan request updated for proposal: {}", command.proposalId());
    }
}