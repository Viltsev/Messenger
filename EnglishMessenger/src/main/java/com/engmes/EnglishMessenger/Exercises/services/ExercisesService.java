package com.engmes.EnglishMessenger.Exercises.services;

import com.engmes.EnglishMessenger.Exercises.models.GetTranslationModel;
import com.engmes.EnglishMessenger.Exercises.models.QuestionExercise;
import com.engmes.EnglishMessenger.Exercises.models.SendAnswerModel;
import com.engmes.EnglishMessenger.Exercises.models.SendTranslationModel;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ExercisesService {
    ResponseEntity getSentenceExercises(String topic) throws IOException, InterruptedException;
    String getQuestionExercises(String level) throws IOException, InterruptedException;
    ResponseEntity sendAnswersExercises(SendAnswerModel answerModel) throws IOException, InterruptedException;
    String getTranslationExercise(GetTranslationModel translationModel) throws IOException, InterruptedException;
    ResponseEntity sendTranslationExercise(SendTranslationModel translationModel) throws IOException, InterruptedException;
}
