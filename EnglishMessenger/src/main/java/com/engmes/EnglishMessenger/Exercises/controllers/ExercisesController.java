package com.engmes.EnglishMessenger.Exercises.controllers;

import com.engmes.EnglishMessenger.Exercises.services.ExercisesService;
import com.engmes.EnglishMessenger.Grammar.model.Match;
import com.engmes.EnglishMessenger.Grammar.service.GrammarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@AllArgsConstructor
public class ExercisesController {
    private final ExercisesService exercisesService;

    @GetMapping("/get_sentences_exercises/{topic}")
    public ResponseEntity getSentencesExercises(@PathVariable String topic) throws IOException, InterruptedException {
        return exercisesService.getSentenceExercises(topic);
    }
}
