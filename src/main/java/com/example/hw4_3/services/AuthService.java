package com.example.hw4_3.services;

import com.example.hw4_3.dto.RegistrationRequest;
import com.example.hw4_3.entities.UserEntity;
import com.example.hw4_3.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegistrationRequest request) throws Exception {
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

    /*public String authenticateUser(AuthenticationRequest request) throws Exception {
        // Поиск пользователя по нику
        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());

        // Проверка наличия пользователя и правильности пароля
        if (user == null || (user.isPresent() && !user.get().getPasswordHash().equals(request.getPassword()))) {
            throw new Exception("Invalid email or password");
        }

        // Генерация JWT токена
        //String token = generateToken(user);

        return token;
    }*/

    /*private String generateToken(UserEntity user) {
        // Генерация JWT токена на основе информации о пользователе (например, идентификатор пользователя, имя пользователя, роли и срок действия)
        // Вам потребуется использовать библиотеку для генерации JWT токенов, например, jjwt или Nimbus JOSE+JWT
        // Здесь приведен пример для jjwt:

        Date expirationDate = calculateTokenExpirationDate();
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "yourSecretKey")
                .compact();

        return token;
    }*/

    private boolean isValidEmail(String email) {
        // Реализация проверки формата электронной почты
        // Например, можно использовать регулярное выражение или сторонние библиотеки для проверки формата
        return email.contains("@");
    }

    private String encryptPassword(String password) {
        // Тут можно задать функцию, но я не стал мудрить
        return password;
    }
}