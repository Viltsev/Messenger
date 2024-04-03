package com.engmes.EnglishMessenger.Theory.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "theory_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id")
    private List<Topic> topics;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "theory_id2")
    private List<Theory> theoryList;
}
