package com.engmes.EnglishMessenger.Chats.services.chatRoomService;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.repo.ChatRoomRepository;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class ChatRoomImplementation implements ChatRoomService {
    private final ChatRoomRepository repository;
    private EntityManager entityManager;
    private final UserService userService;
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

    @Override
    @Transactional
    public String createChatRoom(ChatRoom chatRoom) {
        Optional<User> senderOptional = userService.findByEmail(chatRoom.getSenderId());
        Optional<User> recipientOptional = userService.findByEmail(chatRoom.getRecipientId());

        if (senderOptional.isPresent() && recipientOptional.isPresent()) {
            User sender = senderOptional.get();
            User recipient = recipientOptional.get();

            String chatId = generateChatId(sender.getEmail(), recipient.getEmail());

            ChatRoom checkChat = findChatById(chatId);
            if (checkChat == null) {
                chatRoom.setChatId(chatId);
                saveChat(chatRoom);
                return "Chat successfully saved!";
            } else {
                return "You have the same chat! Error";
            }
        } else {
            return "There are not such users in database";
        }
    }

    @Override
    public String generateChatId(String senderId, String recipientId) {
        String[] ids = {senderId, recipientId};
        Arrays.sort(ids);
        return ids[0] + ids[1];
    }

    @Override
    public List<User> getChatUsers(String email) {
        Stream<ChatRoom> chatRoomStream = findAllChats(email);
        List<User> chatUsers = new ArrayList<>();
        chatRoomStream.forEach(chatRoom -> {
            String foundEmail = chatRoom.getChatId().replace(email, "");
            Optional<User> currentUser = userService.findByEmail(foundEmail);
            currentUser.ifPresent(chatUsers::add);
        });
        return chatUsers;
    }

    @Override
    public String deleteChat(String chatId) {
        ChatRoom chatRoom = findChatById(chatId);
        repository.delete(chatRoom);
        return "Chat has been deleted!";
    }

    @Override
    public String getLastMessage(String chatId) {
        ChatRoom chatRoom = findChatById(chatId);
        return chatRoom.getLastMessage();
    }

}
