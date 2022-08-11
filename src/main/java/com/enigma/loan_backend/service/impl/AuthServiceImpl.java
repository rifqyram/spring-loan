package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.Customer;
import com.enigma.loan_backend.entity.ERole;
import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.impl.UserDetailsImpl;
import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.repository.UserRepository;
import com.enigma.loan_backend.security.JwtUtils;
import com.enigma.loan_backend.service.AuthService;
import com.enigma.loan_backend.service.CustomerService;
import com.enigma.loan_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;

    private final CustomerService customerService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleService roleService, CustomerService customerService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
        this.customerService = customerService;
    }

    @Override
    public UserResponse signUp(AuthRequest request) {
        Role role = roleService.getOrSave(ERole.CUSTOMER.name());
        User user = userRepository.save(request.toUser(role, passwordEncoder));

        customerService.saveCustomer(new Customer(user));

        return user.toUserResponse();
    }

    @Override
    public SignInResponse signIn(AuthRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails);

        String role = getRole(userDetails);
        Role roles = roleService.getOrSave(role);

        return userDetails.toUser(roles).toSignInResponse(jwtToken);
    }

    private String getRole(UserDetailsImpl userDetails) {
        String role = "";
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role = authority.getAuthority();
        }
        return role;
    }



}
