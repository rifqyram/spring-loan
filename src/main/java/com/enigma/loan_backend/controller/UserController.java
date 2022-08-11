package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<?>> getUserById(@PathVariable String id) {
        UserResponse response = userService.get(id);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully get user by id",
                response
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<?>> getUserByToken(Authentication authentication) {
        UserResponse response = userService.getByToken(authentication);
        return ResponseEntity.ok(new CommonResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Successfully get user",
                response
        ));
    }

}
