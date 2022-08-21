package com.enigma.loan_backend.entity.my_enum;

public enum ERole {
    ROLE_CUSTOMER,
    ROLE_STAFF,
    ROLE_ADMIN;


    public static ERole get(String value) {
        for (ERole eRole : ERole.values()) {
            if (eRole.name().equalsIgnoreCase(value)) return eRole;
        }
        throw new RuntimeException("Role not found");
    }


}
