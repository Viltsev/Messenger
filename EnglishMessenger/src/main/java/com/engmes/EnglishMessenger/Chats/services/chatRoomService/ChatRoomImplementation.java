package com.engmes.EnglishMessenger.Chats.services.chatRoomService;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.repo.ChatRoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    private EntityManager entityManager;
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

    @Override
    public ChatRoom findChatById(String chatId) {
        try {
            return entityManager.createQuery("SELECT c FROM ChatRoom c WHERE c.chatId = :chatId", ChatRoom.class)
                    .setParameter("chatId", chatId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ChatRoom updateChatRoom(ChatRoom chatRoom) {
        return repository.save(chatRoom);
    }
}
