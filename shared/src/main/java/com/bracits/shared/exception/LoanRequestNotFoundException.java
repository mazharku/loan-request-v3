package com.bracits.shared.exception;

public class LoanRequestNotFoundException extends LoanRequestException {
    
    public LoanRequestNotFoundException(String loanRequestId) {
        super("Loan request not found with ID: " + loanRequestId);
    }
    
    public LoanRequestNotFoundException(String identifier, String identifierType) {
        super("Loan request not found with " + identifierType + ": " + identifier);
    }
}