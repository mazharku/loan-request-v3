package com.bracits.loanrequest.query.repository;

import com.bracits.loanrequest.query.projection.LoanRequestView;
import com.bracits.shared.domain.enums.LoanRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRequestViewRepository extends MongoRepository<LoanRequestView, String> {
    
    List<LoanRequestView> findByMemberId(String customerId);
    
    List<LoanRequestView> findByStatus(LoanRequestStatus status);
    
    List<LoanRequestView> findByProposalId(String loanProposalId);
}