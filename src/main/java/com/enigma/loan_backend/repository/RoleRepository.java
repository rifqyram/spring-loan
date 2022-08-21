package com.enigma.loan_backend.repository;

import com.enigma.loan_backend.entity.my_enum.ERole;
import com.enigma.loan_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
