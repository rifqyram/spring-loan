package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.service.AuthService;
import com.enigma.loan_backend.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final ValidationUtil validationUtil;

    @Autowired
    public AuthController(AuthService authService, ValidationUtil validationUtil) {
        this.authService = authService;
        this.validationUtil = validationUtil;
    }

    @Operation(summary = "Register")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> signUp(@RequestBody AuthRequest request) {
        validationUtil.validate(request);
        UserResponse user = authService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "User created successfully",
                        user));
    }

    @Operation(summary = "Login")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<?>> signIn(@RequestBody AuthRequest request) {
        validationUtil.validate(request);
        SignInResponse response = authService.signIn(request);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        "Successfully Sign in user",
                        response
                )
        );
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signUpAdmin(@RequestBody AuthRequest request) {
        validationUtil.validate(request);
        UserResponse user = authService.signUpAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        "User created successfully",
                        user));
    }

}
