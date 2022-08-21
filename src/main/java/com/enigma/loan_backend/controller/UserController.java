package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get User")
    @SecurityRequirement(name = "Authorization")
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

    @Operation(summary = "Get User By Token")
    @SecurityRequirement(name = "Authorization")
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
