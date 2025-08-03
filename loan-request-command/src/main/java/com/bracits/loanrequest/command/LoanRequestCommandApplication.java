package com.bracits.loanrequest.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.bracits.loanrequest.command",
    "com.bracits.shared"
})
public class LoanRequestCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanRequestCommandApplication.class, args);
    }
}