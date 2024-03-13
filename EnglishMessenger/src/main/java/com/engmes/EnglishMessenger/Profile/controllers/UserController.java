package com.engmes.EnglishMessenger.Profile.controllers;

import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/user/email")
    public ResponseEntity<String> getUsername(@RequestBody String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getUsername());
        } else {
            // Если пользователь не найден, возвращаем ошибку 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/setFields")
    public void setUserFields( @RequestBody String email,
                               @RequestBody String username,
                               @RequestBody Date dateOfBirth,
                               @RequestBody byte[] photo,
                               @RequestBody List<Long> interests) {
        userService.setFields(email, username, dateOfBirth, photo, interests);
    }
}
