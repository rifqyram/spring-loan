package com.enigma.loan_backend.entity;

public enum ERole {
    CUSTOMER,
    STAFF,
    ADMIN;

    public static ERole get(String value) {
        for (ERole eRole : ERole.values()) {
            if (eRole.name().equalsIgnoreCase(value)) {
                return eRole;
            }
        }
        throw new RuntimeException("Role not found");
    }
}
