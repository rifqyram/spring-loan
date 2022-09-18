package com.enigma.loan_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class LoanCalculation {

    private Double nominal;

    private Double rates;

    private Integer tenor;

    public Double calculate() {
        return (nominal / tenor) + ((rates / 100) * nominal);
    }

}
