package com.engmes.EnglishMessenger.Dialog.services;

import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Interests.services.InterestService;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@AllArgsConstructor
@Primary
public class DialogServiceImplementation implements DialogService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private final UserService userService;

    @Autowired
    private final InterestService interestService;


    @Override
    public User generateDialog(@RequestBody String email) {
        logger.info("POST-метод работает.");
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User currentUser = user.get();
            int currentUserLanguageLevel = userService.getLanguageLevel(currentUser);
            List<User> userList = userService.getAllUsers();

            List<User> sameLevelUsers = new ArrayList<>();
            for (User otherUser : userList) {
                if (!otherUser.getEmail().equals(currentUser.getEmail()) &&
                        userService.getLanguageLevel(otherUser) == currentUserLanguageLevel) {
                    sameLevelUsers.add(otherUser);
                }
            }

            if (!sameLevelUsers.isEmpty()) {
                return findUserWithMostCommonInterests(currentUser, sameLevelUsers);
            } else {
                return findUserWithMostCommonInterests(currentUser, userList);
            }
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity generateDialogTopic(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User closestUser = generateDialog(email);

            if (closestUser != null) {
                String emailClosestUser = closestUser.getEmail();

                List<Interest> currentUserInterests = interestService.getUserInterests(email);
                List<Interest> closestUserInterests = interestService.getUserInterests(emailClosestUser);

                currentUserInterests.retainAll(closestUserInterests);

                if (currentUserInterests.isEmpty()) {
                    int randomIndex = new Random().nextInt(currentUserInterests.size());
                    Interest randomInterest = currentUserInterests.get(randomIndex);
                    return ResponseEntity.ok(randomInterest);
                } else {
                    int randomIndex = new Random().nextInt(currentUserInterests.size());
                    Interest randomInterest = currentUserInterests.get(randomIndex);
                    return ResponseEntity.ok(randomInterest);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет подходящих пользователей.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    private int countCommonInterests(User user1, User user2) {
        Set<Long> interests1 = new HashSet<>(user1.getIdInterests());
        Set<Long> interests2 = new HashSet<>(user2.getIdInterests());
        interests1.retainAll(interests2);
        return interests1.size();
    }

    private User findUserWithMostCommonInterests(User currentUser, List<User> userList) {
        User closestUser = null;
        int maxCommonInterests = -1;

        for (User otherUser : userList) {
            int commonInterests = countCommonInterests(currentUser, otherUser);
            if (commonInterests > maxCommonInterests) {
                maxCommonInterests = commonInterests;
                closestUser = otherUser;
            }
        }

        return closestUser;
    }
}
