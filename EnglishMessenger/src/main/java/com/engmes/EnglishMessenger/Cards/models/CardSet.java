package com.engmes.EnglishMessenger.Cards.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "card_set")
public class CardSet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "card_set_title", nullable = false)
    private String title;
    @Column(name = "card_set_description", nullable = true)
    private String description;
    @Column(name = "user_email_cards", nullable = false)
    private String userEmail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_list_id")
    @Column(name = "card_list")
    private List<Card> cardList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_learn_list_id")
    @Column(name = "to_learn_list")
    private List<Card> toLearn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "learned_list_id")
    @Column(name = "learned_list")
    private List<Card> learned;

}
