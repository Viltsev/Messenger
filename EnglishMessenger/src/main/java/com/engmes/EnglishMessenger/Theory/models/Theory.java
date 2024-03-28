package com.engmes.EnglishMessenger.Theory.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "theory")
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String explanation;
    private String example;
    private String rusExplanation;
    private String level;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "common_mistakes_id")
    private CommonMistakes rusCommonMistakes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "common_mistakes_id")
    private CommonMistakes commonMistakes;
}
