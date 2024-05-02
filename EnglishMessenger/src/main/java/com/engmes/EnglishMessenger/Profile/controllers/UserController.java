package com.engmes.EnglishMessenger.Profile.controllers;

import com.engmes.EnglishMessenger.Profile.model.OnboardingInfo;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/test_photo")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            userService.uploadPhoto(file, "c69f4719-fa278707-76a9-4ddc-bc9e-bc582ad152d2", file.getOriginalFilename());
            return "Image uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image: " + e.getMessage();
        }
    }


    @GetMapping("/get_all_users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get_user_by_username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/set_onboarding")
    public String setUserInfo(@RequestBody OnboardingInfo onboardingInfo) {
        userService.setOnboardingInfo(onboardingInfo);
        return "user info has been added";
    }

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
}
