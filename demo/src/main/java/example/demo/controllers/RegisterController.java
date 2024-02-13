package example.demo.controllers;

import example.demo.data.User;
import example.demo.Repository.UserRepository;
import jakarta.annotation.security.PermitAll;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    @PermitAll
    public ResponseEntity<String> registerUser(@RequestBody User user){
        logger.info("POST-метод работает");

        if (userRepository.findByEmail(user.getEmail()) == null) {

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);
            user.setSalt(saltString);

            String hashedPassword = BCrypt.hashpw(saltString, user.getPassword());
            user.setPassword(hashedPassword);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь с такой почтой уже зарегистрирован");
        }
    }
}