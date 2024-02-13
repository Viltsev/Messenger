package com.engmes.EnglishMessenger.Chats.services.chatMessageService;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.repo.ChatMessageRepository;
import com.engmes.EnglishMessenger.Chats.services.chatMessageService.ChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class ChatMessageServiceImplementation implements ChatMessageService {
    private final ChatMessageRepository repository;
    @Override
    public ChatMessage saveChat(ChatMessage chatMessage) {
        return repository.save(chatMessage);
    }

    @Override
    public Stream<ChatMessage> findAllMessages(String chatId) {
        List<ChatMessage> chatMessageList = repository.findAll();
        return chatMessageList.stream().filter(chatRoom ->
                chatRoom.getSenderId().equals(chatId) || chatRoom.getRecipientId().equals(chatId)
        );
    }
}
