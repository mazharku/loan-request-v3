package com.bracits.loanrequest.command.repository;

import com.bracits.loanrequest.command.domain.LoanRequest;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRequestRepository extends MongoRepository<LoanRequest, String> {
    
    List<LoanRequest> findByMemberId(String memberId);
    
    Optional<LoanRequest> findByProposalId(String proposalId);
    
    List<LoanRequest> findByStatus(LoanRequestStatus status);
    
    Optional<LoanRequest> findByProposalIdAndStatus(String proposalId, LoanRequestStatus status);
    
    boolean existsByProposalIdAndStatus(String proposalId, LoanRequestStatus status);
}