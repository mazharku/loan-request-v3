package com.bracits.shared.exception;

public class LoanRequestProcessingException extends LoanRequestException {
    
    public LoanRequestProcessingException(String message) {
        super(message);
    }
    
    public LoanRequestProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}