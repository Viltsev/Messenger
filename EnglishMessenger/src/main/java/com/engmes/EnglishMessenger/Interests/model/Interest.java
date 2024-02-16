package com.engmes.EnglishMessenger.Interests.model;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest", unique = true)
    private String interest;
}
