package com.enigma.loan_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class LoanCalculation {

    private Double nominal;

    private Double rates;

    public Double calculate() {
        return nominal + ((rates / 100) * nominal);
    }

}
