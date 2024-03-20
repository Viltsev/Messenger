package com.engmes.EnglishMessenger.Exercises.services;

import com.engmes.EnglishMessenger.Exercises.models.QuestionExercise;
import com.engmes.EnglishMessenger.Exercises.models.SendAnswerModel;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ExercisesService {
    ResponseEntity getSentenceExercises(String topic) throws IOException, InterruptedException;
    String getQuestionExercises(String level) throws IOException, InterruptedException;
    ResponseEntity sendAnswersExercises(SendAnswerModel answerModel) throws IOException, InterruptedException;
}
