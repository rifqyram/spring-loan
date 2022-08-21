package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum EInstalmentType {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIXTH_MONTHS(6),
    NINE_MONTHS(9),
    TWELVE_MONTHS(12);

    private final Integer number;

    EInstalmentType(Integer number) {
        this.number = number;
    }

    public static EInstalmentType get(String value) {
        for (EInstalmentType eInstalmentType : values()) {
            if (eInstalmentType.name().equalsIgnoreCase(value)) return eInstalmentType;
        }
        throw new NotFoundException("instalment type not found");
    }



    public Integer getNumber() {
        return number;
    }
}
