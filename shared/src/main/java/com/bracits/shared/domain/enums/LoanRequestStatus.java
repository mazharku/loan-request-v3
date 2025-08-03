package com.bracits.shared.domain.enums;

public enum LoanRequestStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    DISBURSED("Disbursed"),
    CANCELLED("Cancelled");
    
    private final String displayName;
    
    LoanRequestStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}