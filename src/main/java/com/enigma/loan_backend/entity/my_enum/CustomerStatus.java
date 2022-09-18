package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum CustomerStatus {
    ACTIVE,
    INACTIVE;

    public static CustomerStatus get(String value) {
        for (CustomerStatus customerStatus : values()) {
            if (customerStatus.name().equalsIgnoreCase(value)) return customerStatus;
        }
        throw new NotFoundException("customer status not found");
    }
}
