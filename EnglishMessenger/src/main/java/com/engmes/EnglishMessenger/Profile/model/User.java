package com.engmes.EnglishMessenger.Profile.model;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Interests.model.Interest;
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

    @Column(name = "username")
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
    private byte[] photo;

    @ElementCollection
    private List<Long> idInterests;

    @Column(name = "language_level", nullable = true)
    private String languageLevel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_repeat_id")
    @Column(name = "to_repeat_cards", nullable = true)
    private List<Card> toRepeatCards;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_learned_id")
    @Column(name = "learned_cards", nullable = true)
    private List<Card> learnedCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<ChatRoom> chatRoomList;
}
