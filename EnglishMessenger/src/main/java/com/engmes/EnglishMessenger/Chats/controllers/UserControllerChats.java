package com.engmes.EnglishMessenger.Chats.controllers;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.services.chatRoomService.ChatRoomService;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserControllerChats {
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @PostMapping("create_chatroom")
    public String saveChat(@RequestBody ChatRoom chatRoom) {
        Optional<User> senderOptional = userService.findByEmail(chatRoom.getSenderId());
        Optional<User> recipientOptional = userService.findByEmail(chatRoom.getRecipientId());

        if (senderOptional.isPresent() && recipientOptional.isPresent()) {
            User sender = senderOptional.get();
            User recipient = recipientOptional.get();

            ChatRoom senderChatRoom = chatRoom;
            ChatRoom recipientChatRoom = chatRoom;

            String chatId = generateChatId(sender.getEmail(), recipient.getEmail());

            ChatRoom checkChat = chatRoomService.findChatById(chatId);
            if (checkChat == null) {
                updateUserChatRoom(sender, senderChatRoom);
                updateUserChatRoom(recipient, recipientChatRoom);
                chatRoom.setChatId(chatId);
                chatRoomService.saveChat(chatRoom);
                return "Chat successfully saved!";
            } else {
                return "You have the same chat! Error";
            }
        } else {
            return "There are not such users in database";
        }
    }

    @PostMapping("save_message")
    public String saveMessage(@RequestBody ChatMessage chatMessage) {
        String chatId = generateChatId(chatMessage.getSenderId(),
                chatMessage.getRecipientId());
        ChatRoom chatRoom = chatRoomService.findChatById(chatId);
        if (chatRoom != null) {
            updateChatRoomMessage(chatRoom, chatMessage);
            return "Message successfully saved!";
        } else {
            return "There is not a such chatroom in database!";
        }
    }

    @GetMapping("/fetchAllChats/{email}")
    public Stream<ChatRoom> findChatsForUser(@PathVariable String email) {
        return chatRoomService.findAllChats(email);
    }


    // Additional methods
    void updateUserChatRoom(User userChats, ChatRoom chatRoom) {
        List<ChatRoom> chatRoomList = userChats.getChatRoomList();
        chatRoomList.add(chatRoom);
        userChats.setChatRoomList(chatRoomList);
        userService.updateUser(userChats);
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
