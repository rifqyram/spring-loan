package com.enigma.loan_backend.controller;

import com.enigma.loan_backend.model.request.AuthRequest;
import com.enigma.loan_backend.model.response.CommonResponse;
import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import com.enigma.loan_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> signUp(@RequestBody AuthRequest request) {
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
