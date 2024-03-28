package com.engmes.EnglishMessenger.Theory.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "common_mistakes")
public class CommonMistakes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String wrong;
    private String right;
}
