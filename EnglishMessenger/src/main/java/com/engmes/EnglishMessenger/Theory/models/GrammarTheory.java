package com.engmes.EnglishMessenger.Theory.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "grammar_theory")
public class GrammarTheory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "theory_category_id")
    private List<Category> categories;
}
