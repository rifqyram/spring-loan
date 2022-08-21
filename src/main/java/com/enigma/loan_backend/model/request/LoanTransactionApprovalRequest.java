package com.enigma.loan_backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class LoanTransactionApprovalRequest {

    private String loanTransactionId;
    private Double interestRates;
}
