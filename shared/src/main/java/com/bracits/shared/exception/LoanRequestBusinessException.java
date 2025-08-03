package com.bracits.shared.exception;

public class LoanRequestBusinessException extends LoanRequestException {
    
    public LoanRequestBusinessException(String message) {
        super(message);
    }
    
    public LoanRequestBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}