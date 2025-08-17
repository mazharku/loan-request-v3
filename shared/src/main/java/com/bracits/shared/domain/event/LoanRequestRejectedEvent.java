package com.bracits.shared.domain.event;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class LoanRequestRejectedEvent extends DomainEvent {
    
    private final String tracerId;
    private final String memberId;
    private final String proposalId;
    private final String proposalNo;
    private final String loanProductId;
    private final String officeId;
    private final String projectId;
    private final String rejectionReason;
    private final String rejectedBy;
    private final LocalDateTime processedAt;
    
    public LoanRequestRejectedEvent(String aggregateId, String tracerId, String memberId,
                                    String proposalId, String proposalNo, String loanProductId,
                                    String officeId, String projectId, String rejectionReason,
                                    String rejectedBy, LocalDateTime processedAt) {
        super(aggregateId);
        this.tracerId = tracerId;
        this.memberId = memberId;
        this.proposalId = proposalId;
        this.proposalNo = proposalNo;
        this.loanProductId = loanProductId;
        this.officeId = officeId;
        this.projectId = projectId;
        this.rejectionReason = rejectionReason;
        this.rejectedBy = rejectedBy;
        this.processedAt = processedAt;
    }
}