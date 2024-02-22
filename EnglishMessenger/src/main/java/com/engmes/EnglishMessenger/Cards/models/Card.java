package com.engmes.EnglishMessenger.Cards.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private String toLearn;
    private String explanation;
    private String userEmail;
}
