package com.engmes.EnglishMessenger.Exercises.models;

import lombok.Data;

import java.util.List;

@Data
public class TranslationModel {
    private String corrected_text;
    private List<String> explanations;
}
