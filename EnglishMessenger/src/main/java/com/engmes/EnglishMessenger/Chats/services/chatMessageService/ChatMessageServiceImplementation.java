package com.engmes.EnglishMessenger.Chats.services.chatMessageService;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.repo.ChatMessageRepository;
import com.engmes.EnglishMessenger.Chats.services.chatMessageService.ChatMessageService;
import com.engmes.EnglishMessenger.Chats.services.chatRoomService.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class ChatMessageServiceImplementation implements ChatMessageService {
    private final ChatRoomService chatRoomService;
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

    @Override
    public String saveMessage(ChatMessage chatMessage) {
        String chatId = chatRoomService.generateChatId(chatMessage.getSenderId(),
                chatMessage.getRecipientId());
        ChatRoom chatRoom = chatRoomService.findChatById(chatId);
        if (chatRoom != null) {
            updateChatRoomMessage(chatRoom, chatMessage);
            return "Message successfully saved!";
        } else {
            return "There is not a such chatroom in database!";
        }
    }

    @Override
    public void updateChatRoomMessage(ChatRoom chatRoom, ChatMessage chatMessage) {
        List<ChatMessage> chatMessageList = chatRoom.getChatMessageList();
        chatMessageList.add(chatMessage);
        chatRoom.setChatMessageList(chatMessageList);
        chatRoomService.updateChatRoom(chatRoom);
    }
}
