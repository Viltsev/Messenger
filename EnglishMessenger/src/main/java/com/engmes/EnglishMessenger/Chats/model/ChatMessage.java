package com.engmes.EnglishMessenger.Chats.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "chatmessage")
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String content; // text of message
    private String sender; // full name of sender
    private String recipient; // full name of recipient
    private String senderId; // email of sender
    private String recipientId; // email of recipient
    private Date date; // date of message
    private Boolean isRead; // isRead message

    public ChatMessage() {

    }
}
