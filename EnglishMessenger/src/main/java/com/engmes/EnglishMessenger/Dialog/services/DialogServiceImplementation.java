package com.engmes.EnglishMessenger.Dialog.services;

import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class DialogServiceImplementation implements DialogService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private final UserService userService;

    private int[] getUserVector(User user) {
        int languageLevel = userService.getLanguageLevel(user);
        List<Long> userInterests = user.getIdInterests();

        int[] userVector = new int[userInterests.size() + 1];
        userVector[0] = languageLevel;

        int index = 1;
        for (Long interest : userInterests) {
            userVector[index++] = interest.intValue();
        }

        return userVector;
    }

    private double calcEuclideanDistance(int[] vector1, int[] vector2) {
        int maxLength = Math.max(vector1.length, vector2.length);
        double sum = 0.0;
        for (int i = 0; i < maxLength; i++) {
            int value1 = (i < vector1.length) ? vector1[i] : 0;
            int value2 = (i < vector2.length) ? vector2[i] : 0;
            sum += Math.pow(value1 - value2, 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public User generateDialog(@RequestBody String email) {
        logger.info("POST-метод работает.");
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User currentUser = user.get();
            int[] currentUserVector = getUserVector(currentUser);

            String closestUserEmail = null;
            double minDistance = Double.MAX_VALUE;

            List<User> userList = userService.getAllUsers();

            for (User otherUser : userList) {
                if (!otherUser.getEmail().equals(currentUser.getEmail())) {
                    int[] otherUserVector = getUserVector(otherUser);
                    double distance = calcEuclideanDistance(currentUserVector, otherUserVector);

                    if (distance < minDistance) {
                        minDistance = distance;
                        closestUserEmail = otherUser.getEmail();
                    }
                }
            }

            Optional<User> closestUser = userService.findByEmail(closestUserEmail);
            return closestUser.get();
        } else {
            return null;
        }
    }

}
