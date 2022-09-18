package com.enigma.loan_backend.model.request;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collections;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AuthRequest {

    @Email(message = "invalid email")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;

}
