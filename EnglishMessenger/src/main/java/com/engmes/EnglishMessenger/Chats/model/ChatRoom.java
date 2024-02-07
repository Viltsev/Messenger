package com.engmes.EnglishMessenger.Chats.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Documented;
import java.util.Arrays;
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
    private String chatId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private List<ChatMessage> chatMessageList;

//    private String generateChatId(String senderId, String recipientId) {
//        if (chatId == null) {
//            String[] ids = {senderId, recipientId};
//            Arrays.sort(ids);
//            chatId = ids[0] + ids[1];
//        }
//        return chatId;
//    }
}
