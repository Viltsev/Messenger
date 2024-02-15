package com.engmes.EnglishMessenger.Testing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {
    private int questionId;
    private String currentAnswer;
}
