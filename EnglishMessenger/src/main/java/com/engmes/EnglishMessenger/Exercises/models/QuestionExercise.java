package com.engmes.EnglishMessenger.Exercises.models;

import lombok.Data;

import java.util.List;

@Data
public class QuestionExercise {
    private String corrected_answer;
    private List<String> explanation;
}
