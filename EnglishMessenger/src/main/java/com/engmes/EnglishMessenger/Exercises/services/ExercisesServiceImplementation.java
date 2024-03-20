package com.engmes.EnglishMessenger.Exercises.services;

import com.engmes.EnglishMessenger.Exercises.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExercisesServiceImplementation implements ExercisesService {
    private static final Logger logger = LoggerFactory.getLogger(ExercisesServiceImplementation.class);

    @Override
    public ResponseEntity getSentenceExercises(String topic) throws IOException, InterruptedException {
        String apiURL = "http://127.0.0.1:8000/get_sentence_exercise?topic=" + URLEncoder.encode(topic, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SentenceExercise[] exercisesArray = objectMapper.readValue(response.body(), SentenceExercise[].class);
            List<SentenceExercise> sentenceExercises = new ArrayList<>();
            for (SentenceExercise sentenceExercise : exercisesArray) {
                if (sentenceExercise.getExercise().contains("_______")) {
                    sentenceExercises.add(sentenceExercise);
                }
            }
            return ResponseEntity.ok(sentenceExercises);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getQuestionExercises(String level) throws IOException, InterruptedException {
        String apiURL = "http://127.0.0.1:8000/get_question?level=" + URLEncoder.encode(level, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Override
    public ResponseEntity sendAnswersExercises(SendAnswerModel answerModel) throws IOException, InterruptedException {
        String apiURL = "http://127.0.0.1:8000/send_answer?question="
                + URLEncoder.encode(answerModel.getQuestion(), StandardCharsets.UTF_8)
                + "&answer=" + URLEncoder.encode(answerModel.getAnswer(), StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return ResponseEntity.ok(objectMapper.readValue(response.body(), QuestionExercise.class));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getTranslationExercise(GetTranslationModel translationModel) throws IOException, InterruptedException {
        String apiURL = "http://127.0.0.1:8000/get_translation_exercise?topic="
                + URLEncoder.encode(translationModel.getTopic(), StandardCharsets.UTF_8)
                + "&level=" + URLEncoder.encode(translationModel.getLevel(), StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Override
    public ResponseEntity sendTranslationExercise(SendTranslationModel translationModel)
            throws IOException, InterruptedException {
        String apiURL = "http://127.0.0.1:8000/send_translation_exercise?original_text="
                + URLEncoder.encode(translationModel.getOriginal_text(), StandardCharsets.UTF_8)
                + "&text=" + URLEncoder.encode(translationModel.getText(), StandardCharsets.UTF_8)
                + "&level=" + URLEncoder.encode(translationModel.getLevel(), StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return ResponseEntity.ok(objectMapper.readValue(response.body(), TranslationModel.class));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
