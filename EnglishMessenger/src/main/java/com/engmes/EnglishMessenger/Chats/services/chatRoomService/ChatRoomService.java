package com.engmes.EnglishMessenger.Chats.services.chatRoomService;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;

import java.util.stream.Stream;

public interface ChatRoomService {
    ChatRoom saveChat(ChatRoom chatRoom);
    Stream<ChatRoom> findAllChats(String email);
    ChatRoom findChatById(String chatId);
    ChatRoom updateChatRoom(ChatRoom chatRoom);
}
