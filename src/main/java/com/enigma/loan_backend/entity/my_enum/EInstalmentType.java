package com.enigma.loan_backend.entity.my_enum;

import com.enigma.loan_backend.exception.NotFoundException;

public enum EInstalmentType {
    ONE_MONTH("One Month", 1),
    THREE_MONTHS("Three Months", 3),
    SIXTH_MONTHS("Sixth Month", 6),
    NINE_MONTHS("Nine Months", 9),
    TWELVE_MONTHS("Twelve Months", 12);

    private final Integer number;
    private final String name;

    EInstalmentType(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public static EInstalmentType get(String value) {
        for (EInstalmentType eInstalmentType : values()) {
            if (eInstalmentType.name().equalsIgnoreCase(value)) return eInstalmentType;
        }
        throw new NotFoundException("instalment type not found");
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }
}
