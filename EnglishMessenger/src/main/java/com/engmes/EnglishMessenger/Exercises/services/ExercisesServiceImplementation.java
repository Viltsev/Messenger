package com.engmes.EnglishMessenger.Exercises.services;

import com.engmes.EnglishMessenger.Exercises.models.Exercise;
import com.engmes.EnglishMessenger.Profile.services.UserService;
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
        String apiURL = "http://127.0.0.1:8000/get_data?topic=" + URLEncoder.encode(topic, StandardCharsets.UTF_8);
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
            Exercise[] exercisesArray = objectMapper.readValue(response.body(), Exercise[].class);
            List<Exercise> exercises = new ArrayList<>();
            for (Exercise exercise : exercisesArray) {
                if (exercise.getExercise().contains("_______")) {
                    exercises.add(exercise);
                }
            }
            return ResponseEntity.ok(exercises);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
