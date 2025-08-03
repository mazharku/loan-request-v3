package com.bracits.loanrequest.query.listener;

import com.bracits.loanrequest.query.projection.LoanRequestView;
import com.bracits.loanrequest.query.repository.LoanRequestViewRepository;
import com.bracits.shared.constants.ApplicationConstants;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanRequestEventListener {
    
    private final LoanRequestViewRepository repository;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = ApplicationConstants.LOAN_REQUEST_CREATED_QUEUE)
    public void handleLoanRequestCreated(Message message) {
        try {
            String eventJson = new String(message.getBody());
            JsonNode event = objectMapper.readTree(eventJson);
            
            LoanRequestView view = new LoanRequestView();
            view.setId(event.get("aggregateId").asText());
            view.setMemberId(event.get("memberId").asText());
            view.setProposalId(event.get("proposalId").asText());
            view.setProposedLoanAmount(event.get("proposedLoanAmount").asDouble());
            view.setNoOfInstallments(event.get("noOfInstallments").asInt());
            view.setLoanProductId(event.get("loanProductId").asText());
            view.setStatus(LoanRequestStatus.PENDING);
            view.setCreatedAt(LocalDateTime.now());
            view.setUpdatedAt(LocalDateTime.now());
            
            repository.save(view);
            log.info("Created loan request view: {}", view.getId());
        } catch (Exception e) {
            log.error("Failed to handle loan request created event", e);
        }
    }
    
    @RabbitListener(queues = ApplicationConstants.LOAN_REQUEST_APPROVED_QUEUE)
    public void handleLoanRequestApproved(Message message) {
        try {
            String eventJson = new String(message.getBody());
            JsonNode event = objectMapper.readTree(eventJson);
            
            String loanRequestId = event.get("aggregateId").asText();
            LoanRequestView view = repository.findById(loanRequestId).orElse(null);
            
            if (view != null) {
                view.setApprovedLoanAmount(event.get("approvedLoanAmount").asDouble());
                view.setStatus(LoanRequestStatus.APPROVED);
                view.setProcessedBy(event.get("approvedBy").asText());
                view.setRemarks(event.get("remarks").asText());
                view.setProcessedAt(LocalDateTime.now());
                view.setUpdatedAt(LocalDateTime.now());
                
                repository.save(view);
                log.info("Updated loan request view as approved: {}", view.getId());
            }
        } catch (Exception e) {
            log.error("Failed to handle loan request approved event", e);
        }
    }
    
    @RabbitListener(queues = ApplicationConstants.LOAN_REQUEST_REJECTED_QUEUE)
    public void handleLoanRequestRejected(Message message) {
        try {
            String eventJson = new String(message.getBody());
            JsonNode event = objectMapper.readTree(eventJson);
            
            String loanRequestId = event.get("aggregateId").asText();
            LoanRequestView view = repository.findById(loanRequestId).orElse(null);
            
            if (view != null) {
                view.setStatus(LoanRequestStatus.REJECTED);
                view.setProcessedBy(event.get("rejectedBy").asText());
                view.setRemarks(event.get("reason").asText());
                view.setProcessedAt(LocalDateTime.now());
                view.setUpdatedAt(LocalDateTime.now());
                
                repository.save(view);
                log.info("Updated loan request view as rejected: {}", view.getId());
            }
        } catch (Exception e) {
            log.error("Failed to handle loan request rejected event", e);
        }
    }
    
    @RabbitListener(queues = ApplicationConstants.LOAN_REQUEST_UPDATE_QUEUE)
    public void handleLoanRequestUpdated(Message message) {
        try {
            String eventJson = new String(message.getBody());
            JsonNode event = objectMapper.readTree(eventJson);
            
            String loanRequestId = event.get("aggregateId").asText();
            LoanRequestView view = repository.findById(loanRequestId).orElse(null);
            
            if (view != null) {
                view.setMemberId(event.get("memberId").asText());
                view.setProposalId(event.get("proposalId").asText());
                view.setProposedLoanAmount(event.get("proposedLoanAmount").asDouble());
                view.setNoOfInstallments(event.get("noOfInstallments").asInt());
                view.setLoanProductId(event.get("loanProductId").asText());
                view.setUpdatedAt(LocalDateTime.now());
                
                repository.save(view);
                log.info("Updated loan request view: {}", view.getId());
            }
        } catch (Exception e) {
            log.error("Failed to handle loan request updated event", e);
        }
    }
}