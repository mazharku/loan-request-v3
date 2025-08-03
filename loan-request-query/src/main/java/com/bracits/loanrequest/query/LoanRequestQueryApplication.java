package com.bracits.loanrequest.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.bracits.loanrequest.query",
    "com.bracits.shared"
})
public class LoanRequestQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanRequestQueryApplication.class, args);
    }
}