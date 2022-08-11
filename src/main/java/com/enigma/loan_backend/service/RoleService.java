package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.Role;

public interface RoleService {

    Role getOrSave(String role);

}
