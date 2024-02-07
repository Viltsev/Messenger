package com.engmes.EnglishMessenger.Chats.controllers;


import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    // private final ChatRoomService chatRoomService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @MessageMapping("/chat.sendMessage/{senderId}/{recipientId}")
    @SendTo("/topic/public/{senderId}/{recipientId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getType() != ChatMessage.MessageType.JOIN) {
//            ChatRoom chatRoom = new ChatRoom();
//            chatRoom.setSenderId(chatMessage.getSenderId());
//            chatRoom.setRecipientId(chatMessage.getRecipientId());
//            chatRoomService.saveChat(chatRoom);
            logger.info("senderId: " + chatMessage.getSenderId() + " recipientId" + chatMessage.getRecipientId());
            logger.info("new message: " + chatMessage.getContent() + " from sender: " + chatMessage.getSender());
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{senderId}/{recipientId}")
    @SendTo("/topic/public/{senderId}/{recipientId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
