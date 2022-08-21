package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum LoanStatus {
    PAID,
    UNPAID;

    public static LoanStatus get(String value) {
        for (LoanStatus loanStatus : values()) {
            if (loanStatus.name().equalsIgnoreCase(value)) {
                return loanStatus;
            }
        }
        throw new NotFoundException("loan status not found");
    }
}
