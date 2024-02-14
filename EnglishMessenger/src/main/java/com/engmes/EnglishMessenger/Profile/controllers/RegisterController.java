package com.engmes.EnglishMessenger.Profile.controllers;

import com.engmes.EnglishMessenger.Profile.models.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PermitAll
    public ResponseEntity<String> registerUser(@RequestBody User user){
        logger.info("POST-метод работает");

        if (userRepository.findByEmail(user.getEmail()) == null) {

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String saltString = BCrypt.gensalt();
            user.setSalt(saltString);

            String hashedPassword = BCrypt.hashpw(user.getPassword(), saltString);
            user.setPassword(hashedPassword);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с такой почтой уже зарегистрирован");
        }
    }
}
