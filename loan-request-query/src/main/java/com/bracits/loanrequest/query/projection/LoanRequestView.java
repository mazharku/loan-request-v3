package com.bracits.loanrequest.query.projection;

import com.bracits.shared.domain.enums.LoanRequestStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "loan_request_views")
@Data
public class LoanRequestView {
    
    @Id
    private String id;
    private String memberId;
    private String proposalId;
    private String loanProductId;
    private Double proposedLoanAmount;
    private Double approvedLoanAmount;
    private Double interestRate;
    private Integer noOfInstallments;
    private Integer approvedDurationInMonths;
    private LoanRequestStatus status;
    private String remarks;
    private String processedBy;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}