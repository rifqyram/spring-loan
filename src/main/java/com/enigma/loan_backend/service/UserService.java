package com.enigma.loan_backend.service;

import com.enigma.loan_backend.model.response.UserResponse;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserResponse get(String id);

    UserResponse getByToken(Authentication authentication);

}
