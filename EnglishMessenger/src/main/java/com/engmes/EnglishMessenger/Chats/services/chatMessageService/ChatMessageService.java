package com.engmes.EnglishMessenger.Chats.services.chatMessageService;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;

import java.util.stream.Stream;


public interface ChatMessageService {
    ChatMessage saveChat(ChatMessage chatMessage);
    Stream<ChatMessage> findAllMessages(String chatId);
}
