package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class SignInResponse {

    private String email;

    private String role;

    private String token;

    public SignInResponse(UserDetailsImpl user, String role, String token) {
        this.email = user.getEmail();
        this.role = role;
        this.token = token;
    }
}
