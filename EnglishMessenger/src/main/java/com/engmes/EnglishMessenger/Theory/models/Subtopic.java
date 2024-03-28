package com.engmes.EnglishMessenger.Theory.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "subtopic")
public class Subtopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "theory_id")
    private List<Theory> theoryList;
}
