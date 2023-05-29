package com.example.hw4_3.services;

import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.entities.SessionEntity;
import com.example.hw4_3.entities.UserEntity;
import com.example.hw4_3.repositories.SessionRepository;
import com.example.hw4_3.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    // Секретный ключ для подписи JWT
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Время действия токена (30 минут)
    private final long expirationTime = 30 * 60 * 1000;

    public void registerUser(@RequestBody RegistrationRequest request) throws Exception {
        // Validate input data
        try {
            validateRegistrationRequest(request);
        } catch (Exception e) {
            throw new Exception();
        }

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


    private void validateRegistrationRequest(RegistrationRequest request) throws Exception {
        if (request.getUsername().isBlank() || request.getPassword().isBlank() || request.getEmail().isBlank()) {
            throw new Exception("Missing required fields");
        }

        if (!isValidEmail(request.getEmail())) {
            throw new Exception("Invalid email");
        }

    }

    public String authenticate(String email, String password) throws Exception {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !password.equals(user.get().getPasswordHash())) {
            throw new Exception("Invalid email or password");
        }



        // Генерация JWT
        String token = Jwts.builder()
                .setSubject(user.get().getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();


        Integer userId = userRepository.findByEmail(email).get().getId();
        SessionEntity session = new SessionEntity();
        session.setToken(token);
        session.setUser_id(userId);
        session.setExpires_at(LocalDateTime.now().plusMinutes(30));
        sessionRepository.save(session);


        return token;
    }

    public String getUsernameFromToken(String token) throws Exception {


        Optional<SessionEntity> session = sessionRepository.findByToken(token);

        if(LocalDateTime.now().isAfter(session.get().getExpires_at())){
            throw new Exception("Token is already invalid");
        }

        return userRepository.findById(session.get().getUser_id()).get().getEmail();

    }

    public Optional<UserEntity> getUserByEmail(String username){
        return userRepository.findByEmail(username);
    }

    private boolean isValidEmail(String email) {
        // Реализация проверки формата электронной почты
        // Например, можно использовать регулярное выражение или сторонние библиотеки для проверки формата
        return email.contains("@");
    }

    private String encryptPassword(String password) {
        // Тут можно задать функцию для хэширования, но я не стал мудрить
        return password;
    }
}