package com.engmes.EnglishMessenger.Profile.model;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;
//import com.engmes.EnglishMessenger.Cards.models.Cards;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", nullable = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "salt", nullable = true)
    private String salt;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "date_of_birth", nullable = true)
    private Date dateOfBirth;

    @Column(name = "photo", nullable = true)
    private String photo;

    @ElementCollection
    private List<Long> idInterests;

    @Column(name = "language_level", nullable = true)
    private String languageLevel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cards_id")
    @Column(name = "card_sets", nullable = true)
    private List<CardSet> cardSets;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cards_id_tl")
    @Column(name = "card_to_learn", nullable = true)
    private List<Card> cardsToLearn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cards_id_tld")
    @Column(name = "card_learned", nullable = true)
    private List<Card> cardsLearned;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<ChatRoom> chatRoomList;
}
