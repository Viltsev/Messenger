package com.engmes.EnglishMessenger.Dictionary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dictionary")
public class Word {
    @Id
    private String id;

    @Column(name = "word")
    private String word;

    @Column(name = "description")
    private String description;
}