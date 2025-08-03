package com.bracits.shared.exception;

public class LoanRequestValidationException extends LoanRequestException {
    
    public LoanRequestValidationException(String message) {
        super(message);
    }
    
    public LoanRequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}