package com.enigma.loan_backend.model.response;

import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UserResponse {

    private String email;

    private String role;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.role = !user.getRoles().isEmpty() ? user.getRoles().get(0).getRole().name() : null;
    }

    public UserResponse(UserDetailsImpl userDetails, String role) {
        this.email = userDetails.getEmail();
        this.role = role;
    }
}
