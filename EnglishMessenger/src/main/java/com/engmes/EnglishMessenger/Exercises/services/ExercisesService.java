package com.engmes.EnglishMessenger.Exercises.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ExercisesService {
    ResponseEntity getSentenceExercises(String topic) throws IOException, InterruptedException;
}
