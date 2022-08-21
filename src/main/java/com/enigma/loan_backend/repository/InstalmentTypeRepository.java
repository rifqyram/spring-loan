package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.InstalmentType;
import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
    Optional<InstalmentType> findByInstalmentType(EInstalmentType instalmentType);
}
