package com.enigma.loan_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class SignInResponse {

    private String email;

    private String role;

    private String token;

}
