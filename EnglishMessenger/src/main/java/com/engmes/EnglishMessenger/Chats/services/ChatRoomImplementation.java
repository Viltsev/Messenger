package com.engmes.EnglishMessenger.Chats.services;

import com.engmes.EnglishMessenger.Chats.controllers.WebSocketEventListener;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.model.User;
import com.engmes.EnglishMessenger.Chats.repo.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class ChatRoomImplementation implements ChatRoomService {
    private final ChatRoomRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomImplementation.class);
    @Override
    public ChatRoom saveChat(ChatRoom chatRoom) {
        logger.info("new chat has been saved!");
        return repository.save(chatRoom);
    }

    @Override
    public Stream<ChatRoom> findAllChats(String email) {
        List<ChatRoom> chatRooms = repository.findAll();
        return chatRooms.stream().filter(chatRoom ->
            chatRoom.getSenderId().equals(email) || chatRoom.getRecipientId().equals(email)
        );
    }
}
