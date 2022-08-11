package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.repository.RoleRepository;
import com.enigma.loan_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.enigma.loan_backend.entity.ERole.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getOrSave(String strRole) {
        if (strRole == null || strRole.isEmpty()) {
            if (!roleRepository.existsByRole(CUSTOMER)) return roleRepository.save(new Role(CUSTOMER));
            return roleRepository.findByRole(CUSTOMER).orElseThrow(() -> new NotFoundException("Role not found"));
        } else if (strRole.equalsIgnoreCase("staff")) {
            if (!roleRepository.existsByRole(STAFF)) return roleRepository.save(new Role(STAFF));
            return roleRepository.findByRole(STAFF).orElseThrow(() -> new NotFoundException("Role not found"));
        } else if (strRole.equalsIgnoreCase("admin")) {
            if (!roleRepository.existsByRole(ADMIN)) return roleRepository.save(new Role(ADMIN));
            return roleRepository.findByRole(ADMIN).orElseThrow(() -> new NotFoundException("Role not found"));
        } else {
            if (!roleRepository.existsByRole(CUSTOMER)) return roleRepository.save(new Role(CUSTOMER));
            return roleRepository.findByRole(CUSTOMER).orElseThrow(() -> new NotFoundException("Role not found"));
        }
    }

}
