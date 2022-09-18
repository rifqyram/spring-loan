package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.InstalmentTypeResponse;
import com.enigma.loan_backend.repository.InstalmentTypeRepository;
import com.enigma.loan_backend.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public InstalmentTypeResponse get(String id) {
        InstalmentType instalmentType = findByIdOrThrowNotFound(id);
        return new InstalmentTypeResponse(instalmentType.getId(), instalmentType.getInstalmentType().getName());
    }

    @Override
    public InstalmentType findById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public InstalmentTypeResponse getByType(EInstalmentType type) {
        InstalmentType instalmentType = instalmentTypeRepository.findByInstalmentType(type).orElseThrow(() -> new NotFoundException("instalment type not found"));
        return new InstalmentTypeResponse(instalmentType.getId(), instalmentType.getInstalmentType().getName());
    }

    @Override
    public List<InstalmentTypeResponse> getAll() {
        return instalmentTypeRepository.findAll().stream().map(instalmentType -> new InstalmentTypeResponse(instalmentType.getId(), instalmentType.getInstalmentType().getName())).collect(Collectors.toList());
    }

    private InstalmentType findByIdOrThrowNotFound(String id) {
        return instalmentTypeRepository
                .findById(id).orElseThrow(() -> new NotFoundException(String.format("instalment type not found with id %s", id)));
    }
}
