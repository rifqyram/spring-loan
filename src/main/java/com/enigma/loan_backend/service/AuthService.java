package com.enigma.loan_backend.service;

import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;

public interface AuthService {

    UserResponse signUp(AuthRequest request);

    SignInResponse signIn(AuthRequest request);

    UserResponse signUpAdmin(AuthRequest request);

}
