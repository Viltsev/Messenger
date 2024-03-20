package com.engmes.EnglishMessenger.Exercises.controllers;

import com.engmes.EnglishMessenger.Exercises.models.SendAnswerModel;
import com.engmes.EnglishMessenger.Exercises.services.ExercisesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/exercises")
@AllArgsConstructor
public class ExercisesController {
    private final ExercisesService exercisesService;

    // get grammar exercises
    @GetMapping("/get_sentences_exercises/{topic}")
    public ResponseEntity getSentencesExercises(@PathVariable String topic) throws IOException, InterruptedException {
        return exercisesService.getSentenceExercises(topic);
    }

    // get question exercise
    @GetMapping("/get_question_exercise/{level}")
    public String getQuestionExercise(@PathVariable String level) throws IOException, InterruptedException {
        return exercisesService.getQuestionExercises(level);
    }

    // sending user answer to question and getting corrected version of answer with explanations
    @GetMapping("/send_answer_exercise")
    public ResponseEntity sendAnswerExercise(@RequestBody SendAnswerModel answerModel) throws IOException, InterruptedException {
        return exercisesService.sendAnswersExercises(answerModel);
    }
}
