package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.impl.UserDetailsImpl;
import com.enigma.loan_backend.entity.my_enum.ERole;
import com.enigma.loan_backend.exception.NotFoundException;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.UserRepository;
import com.enigma.loan_backend.service.RoleService;
import com.enigma.loan_backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse get(String id) {
        return new UserResponse(findByIdOrThrowNotFound(id));
    }

    @Override
    public UserResponse getByToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = getRole(userDetails);
        return new UserResponse(userDetails, role);
    }

    @Override
    public User findByToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return findByIdOrThrowNotFound(userDetails.getId());
    }

    private User findByIdOrThrowNotFound(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private String getRole(UserDetailsImpl userDetails) {
        String role = "";
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role = authority.getAuthority();
        }
        return role;
    }
}
