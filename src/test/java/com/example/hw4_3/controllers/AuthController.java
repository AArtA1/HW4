package com.example.hw4_3.controllers;

import com.example.hw4_3.dto.AuthenticationRequest;
import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.entities.UserEntity;
import com.example.hw4_3.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            authService.registerUser(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Incorrect input data");
        }
    }


    // Other authentication endpoints: login, retrieve user information, etc.
}