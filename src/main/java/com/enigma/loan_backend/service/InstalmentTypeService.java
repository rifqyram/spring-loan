package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;

public interface InstalmentTypeService {

    InstalmentType create(InstalmentType instalmentType);

    InstalmentType get(String id);

    InstalmentType getByType(EInstalmentType type);

}
