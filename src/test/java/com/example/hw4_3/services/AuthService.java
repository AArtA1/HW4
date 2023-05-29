package com.example.hw4_3.services;

import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.entities.UserEntity;
import com.example.hw4_3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegistrationRequest request) {
        // Validate input data
        validateRegistrationRequest(request);

        // Create a new user entity
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(encryptPassword(request.getPassword()));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Save the user to the database
        userRepository.save(user);
    }

    private void validateRegistrationRequest(RegistrationRequest request) {
        // Validate the registration request, e.g., check email format, password strength, etc.
    }

    private String encryptPassword(String password) {
        return "123";
        // Encrypt the password using a secure hashing algorithm
    }
}