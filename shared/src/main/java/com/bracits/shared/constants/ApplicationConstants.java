package com.bracits.shared.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {
    
    // RabbitMQ Exchange Names
    public static final String LOAN_REQUEST_FANOUT_EXCHANGE = "loan.request.fanout";
    
    // RabbitMQ Queue Names
    public static final String LOAN_REQUEST_CREATED_QUEUE = "loan.request.created.query";
    public static final String LOAN_REQUEST_APPROVED_QUEUE = "loan.request.approved.query";
    public static final String LOAN_REQUEST_REJECTED_QUEUE = "loan.request.rejected.query";
    public static final String LOAN_REQUEST_UPDATE_QUEUE = "loan.request.updated.query";

}