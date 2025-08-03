package com.bracits.shared.exception;

public abstract class LoanRequestException extends RuntimeException {
    
    protected LoanRequestException(String message) {
        super(message);
    }
    
    protected LoanRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}