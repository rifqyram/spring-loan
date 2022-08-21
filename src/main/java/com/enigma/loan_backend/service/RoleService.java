package com.enigma.loan_backend.service;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.my_enum.ERole;

public interface RoleService {

    Role getOrSave(ERole role);

}
