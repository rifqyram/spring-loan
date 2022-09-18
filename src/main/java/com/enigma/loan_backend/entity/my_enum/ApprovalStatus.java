package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum ApprovalStatus {
    ON_PROGRESS("On Progress"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String status;

    ApprovalStatus(String status) {
        this.status = status;
    }

    public static ApprovalStatus get(String value) {
        for (ApprovalStatus approvalStatus : values()) {
            if (approvalStatus.name().equalsIgnoreCase(value)) return approvalStatus;
        }
        throw new NotFoundException("status not found");
    }

    public String getStatus() {
        return status;
    }
}
