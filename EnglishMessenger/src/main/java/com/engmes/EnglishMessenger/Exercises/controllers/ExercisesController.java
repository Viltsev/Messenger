package com.engmes.EnglishMessenger.Exercises.controllers;

import com.engmes.EnglishMessenger.Exercises.models.GetTranslationModel;
import com.engmes.EnglishMessenger.Exercises.models.SendAnswerModel;
import com.engmes.EnglishMessenger.Exercises.models.SendTranslationModel;
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
    public ResponseEntity sendAnswerExercise(@RequestBody SendAnswerModel answerModel)
            throws IOException, InterruptedException {
        return exercisesService.sendAnswersExercises(answerModel);
    }

    // getting text to translate it
    @GetMapping("/get_translation_exercise")
    public String getTranslationExercise(@RequestBody GetTranslationModel translationModel)
            throws IOException, InterruptedException {
        return exercisesService.getTranslationExercise(translationModel);
    }


    // sending translation of text and getting correct version and explanations
    @GetMapping("/send_translation_exercise")
    public ResponseEntity sendTranslationExercise(@RequestBody SendTranslationModel translationModel)
            throws IOException, InterruptedException {
        return exercisesService.sendTranslationExercise(translationModel);
    }
}
