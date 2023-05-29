package com.example.hw4_3.controllers;

import com.example.hw4_3.dto.AuthenticationRequest;
import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.entities.UserEntity;
import com.example.hw4_3.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            return ResponseEntity.badRequest().body("Occurred creating error.");
        }
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String token = authService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<UserEntity> getUser(@RequestParam String token) {
        String username;
        Optional<UserEntity> user = Optional.empty();
        try{
            username = authService.getUsernameFromToken(token);
            user = authService.getUserByEmail(username);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Other authentication endpoints: login, retrieve user information, etc.
}