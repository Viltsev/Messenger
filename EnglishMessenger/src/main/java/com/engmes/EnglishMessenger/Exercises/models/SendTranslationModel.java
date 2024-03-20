package com.engmes.EnglishMessenger.Exercises.models;

import lombok.Data;

@Data
public class SendTranslationModel {
    private String original_text;
    private String text;
    private String level;
}
