package com.engmes.EnglishMessenger.Testing.controllers;

import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import com.engmes.EnglishMessenger.Testing.model.Answer;
import com.engmes.EnglishMessenger.Testing.model.Question;
import com.engmes.EnglishMessenger.Testing.services.TestService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

@RestController
@RequestMapping("/api/v1/testing")
@AllArgsConstructor
public class TestController {
    private final TestService service;
    private final UserService userService;

    @PostMapping("/saveQuestions")
    public void saveQuestion(@RequestBody List<Question> questionList) {
        questionList.forEach(service::saveQuestion);
    }

    @GetMapping("/fetchAllQuestions")
    public List<Question> fetchQuestions() {
        return service.getAllQuestions();
    }

    @PostMapping("/getCurrentLevel/{email}")
    public String getCurrentLevel(@PathVariable String email, @RequestBody List<Answer> answerList) {
        List<Question> questionList = service.getAllQuestions();
        AtomicInteger points = new AtomicInteger();

        answerList.forEach(answer -> {
            Question question = service.findQuestionById((long) answer.getQuestionId());
            String rightAnswer = question.getRightAnswer();
            if (answer.getCurrentAnswer().equals(rightAnswer)) {
                points.addAndGet(1);
            }
        });

        String level = getLanguageLevel.apply(points.get());

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setLanguageLevel(level);
            userService.updateUser(currentUser);
            return level;
        } else {
            return "User not found. There are not possibility to update language level";
        }
    }

    static IntFunction<String> getLanguageLevel = points -> {
        if (points >= 0 && points <= 8) {
            return "A1";
        } else if (points >= 9 && points <= 12) {
            return "A2";
        } else if (points >= 13 && points <= 15) {
            return "B1";
        } else if (points >= 16 && points <= 20) {
            return "B2";
        } else if (points >= 21 && points <= 24) {
            return "C1";
        } else if (points == 25) {
            return "C2";
        } else {
            return "Неизвестный уровень";
        }
    };
}
