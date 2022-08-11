package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.service.AuthService;
import com.enigma.loan_backend.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ValidationUtil validationUtil;

    @Autowired
    public AuthController(AuthService authService, ValidationUtil validationUtil) {
        this.authService = authService;
        this.validationUtil = validationUtil;
    }

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

}
