package com.engmes.EnglishMessenger.Chats.services.chatRoomService;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Profile.model.User;

import java.util.List;
import java.util.stream.Stream;

public interface ChatRoomService {
    ChatRoom saveChat(ChatRoom chatRoom);
    Stream<ChatRoom> findAllChats(String email);
    ChatRoom findChatById(String chatId);
    ChatRoom updateChatRoom(ChatRoom chatRoom);
    String createChatRoom(ChatRoom chatRoom);
    String generateChatId(String senderId, String recipientId);
    void updateUserChatRoom(User userChats, ChatRoom chatRoom);
    List<User> getChatUsers(String email);
    String deleteChat(String chatId);
}
