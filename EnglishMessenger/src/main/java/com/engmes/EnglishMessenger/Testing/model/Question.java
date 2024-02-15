package com.engmes.EnglishMessenger.Testing.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_question")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    @Column(nullable = true)
    private String answerFour;
    private String rightAnswer;
}
