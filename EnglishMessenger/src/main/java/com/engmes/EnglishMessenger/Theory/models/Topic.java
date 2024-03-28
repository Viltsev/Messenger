package com.engmes.EnglishMessenger.Theory.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "theory_id")
    private List<Theory> theoryList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subtopic_id")
    private List<Subtopic> subtopicList;
}
