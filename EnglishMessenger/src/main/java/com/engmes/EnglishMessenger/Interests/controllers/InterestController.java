package com.engmes.EnglishMessenger.Interests.controllers;


import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Interests.services.InterestService;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import com.engmes.EnglishMessenger.Testing.model.Answer;
import com.engmes.EnglishMessenger.Testing.model.Question;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interests")
@AllArgsConstructor
public class InterestController {
    private static final Logger logger = LoggerFactory.getLogger(InterestController.class);
    @Autowired
    private final InterestService interestService;

    @Autowired
    private final UserService userService;

    @GetMapping("/getAll")
    public List<Interest> getInterests() {
        return interestService.getAllInterests();
    }

    @PostMapping("/saveAll")
    public void saveInterest(@RequestBody List<Interest> interestList) {
        interestList.forEach(interestService::saveInterest);
    }

    @PostMapping("/save/{email}")
    public String saveUserInterests(@PathVariable String email, @RequestBody List<Long> interestsList) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setIdInterests(interestsList);
            userService.updateUser(currentUser);
            return "Интересы сохранены.";
        } else {
            return "Пользователь не найден.";
        }
    }
}
