package com.engmes.EnglishMessenger.Chats.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Documented;
import java.util.List;

@Data
@Entity
@Table(name = "chatrooms")
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;
    private String senderId;
    private String recipientId;
    private String chatId = senderId + recipientId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private List<ChatMessage> chatMessageList;
}
