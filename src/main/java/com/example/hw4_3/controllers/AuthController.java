package com.example.hw4_3.controllers;

import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            authService.registerUser(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.ok("User Registered");
        }
    }


    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<String> get(@RequestParam String str){
        return ResponseEntity.ok("kruto" + str);
    }


    // Other authentication endpoints: login, retrieve user information, etc.
}