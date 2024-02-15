package com.engmes.EnglishMessenger.Chats.controllers;


import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.services.chatRoomService.ChatRoomService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @MessageMapping("/chat.sendMessage/{chatId}")
    @SendTo("/topic/public/{chatId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        String chatId = generateChatId(chatMessage.getSenderId(),
                chatMessage.getRecipientId());
        ChatRoom chatRoom = chatRoomService.findChatById(chatId);
        if (chatRoom != null) {
            logger.info("\nnew message: " + chatMessage.getContent() + "\nfrom sender: " + chatMessage.getSender());
            updateChatRoomMessage(chatRoom, chatMessage);
            logger.info("message successfully saved!");
        } else {
            logger.info("there is not a such chatroom in database!");
        }
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{chatId}")
    @SendTo("/topic/public/{chatId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    void updateChatRoomMessage(ChatRoom chatRoom, ChatMessage chatMessage) {
        List<ChatMessage> chatMessageList = chatRoom.getChatMessageList();
        chatMessageList.add(chatMessage);
        chatRoom.setChatMessageList(chatMessageList);
        chatRoomService.updateChatRoom(chatRoom);
    }

    String generateChatId(String senderId, String recipientId) {
        String[] ids = {senderId, recipientId};
        Arrays.sort(ids);
        return ids[0] + ids[1];
    }

}
