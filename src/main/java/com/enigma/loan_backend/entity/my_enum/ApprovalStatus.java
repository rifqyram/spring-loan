package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum ApprovalStatus {
    APPROVED,
    REJECTED;

    public static ApprovalStatus get(String value) {
        for (ApprovalStatus approvalStatus : values()) {
            if (approvalStatus.name().equalsIgnoreCase(value)) return approvalStatus;
        }
        throw new NotFoundException("status not found");
    }

}
