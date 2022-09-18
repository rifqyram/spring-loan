package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.model.response.InstalmentTypeResponse;

import java.util.List;

public interface InstalmentTypeService {

    InstalmentType create(InstalmentType instalmentType);

    InstalmentTypeResponse get(String id);

    InstalmentType findById(String id);

    InstalmentTypeResponse getByType(EInstalmentType type);

    List<InstalmentTypeResponse> getAll();

}
