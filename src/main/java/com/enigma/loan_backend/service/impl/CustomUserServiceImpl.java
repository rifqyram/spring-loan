package com.enigma.loan_backend.service.impl;

import com.enigma.loan_backend.entity.User;
import com.enigma.loan_backend.entity.impl.UserDetailsImpl;
import com.enigma.loan_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImpl(user);
    }
}
