package com.enigma.loan_backend.model.request;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AuthRequest {

    private String email;

    private String password;

    public User toUser(Role role, PasswordEncoder passwordEncoder) {
        return new User(email, passwordEncoder.encode(password), Collections.singletonList(role));
    }

    public User toUser(Role role) {
        return new User(email, password, Collections.singletonList(role));
    }

}
