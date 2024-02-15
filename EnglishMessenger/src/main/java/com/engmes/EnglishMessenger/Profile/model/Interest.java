package com.engmes.EnglishMessenger.Profile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest", unique = true)
    private String interest;
}
