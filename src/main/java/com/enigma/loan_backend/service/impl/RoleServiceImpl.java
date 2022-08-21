package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.my_enum.ERole;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.repository.RoleRepository;
import com.enigma.loan_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.enigma.loan_backend.entity.my_enum.ERole.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role).orElseGet(() -> roleRepository.save(new Role(role)));
    }

}
