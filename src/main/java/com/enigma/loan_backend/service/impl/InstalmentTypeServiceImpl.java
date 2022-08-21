package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.repository.InstalmentTypeRepository;
import com.enigma.loan_backend.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstalmentTypeServiceImpl implements InstalmentTypeService {
    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    public InstalmentType create(InstalmentType instalmentType) {
        return instalmentTypeRepository.findByInstalmentType(instalmentType.getInstalmentType())
                .orElseGet(() -> instalmentTypeRepository.save(instalmentType));
    }

    @Override
    public InstalmentType get(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public InstalmentType getByType(EInstalmentType type) {
        return instalmentTypeRepository.findByInstalmentType(type).orElseThrow(() -> new NotFoundException("instalment type not found"));
    }

    private InstalmentType findByIdOrThrowNotFound(String id) {
        return instalmentTypeRepository
                .findById(id).orElseThrow(() -> new NotFoundException(String.format("instalment type not found with id %s", id)));
    }
}
