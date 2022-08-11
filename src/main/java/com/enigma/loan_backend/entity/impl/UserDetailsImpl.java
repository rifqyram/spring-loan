package com.enigma.loan_backend.entity.impl;

import com.enigma.loan_backend.entity.Role;
import com.enigma.loan_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String id;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User toUser(Role role) {
        return new User(email, password, Collections.singletonList(role));
    }
}
