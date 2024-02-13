package com.engmes.EnglishMessenger.Chats.controllers;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.model.UserChats;
import com.engmes.EnglishMessenger.Chats.services.chatRoomService.ChatRoomService;
import com.engmes.EnglishMessenger.Chats.services.userService.UserServiceChats;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserControllerChats {

    private final UserServiceChats service;
    private final ChatRoomService chatRoomService;
    private static final Logger logger = LoggerFactory.getLogger(UserControllerChats.class);

    @GetMapping("/fetchAllUsers/{email}")
    public Stream<UserChats> findAllStudent(@PathVariable String email) {
        return service.findAllUsers(email);
    }

    @PostMapping("register")
    public String saveUser(@RequestBody UserChats userChats) {
        service.saveUser(userChats);
        return "User successfully saved!";
    }

    @GetMapping("/authByEmail/{email}")
    public UserChats findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/findFriend/{email}")
    public UserChats findFriendByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PutMapping("update_user")
    public UserChats updateStudent(UserChats userChats) {
        return service.updateUser(userChats);
    }

    @DeleteMapping("delete_user/{email}")
    public void deleteStudent(@PathVariable String email) {
        service.deleteUser(email);
    }

    @PostMapping("create_chatroom")
    public String saveChat(@RequestBody ChatRoom chatRoom) {
        UserChats sender = service.getUserByEmail(chatRoom.getSenderId());
        UserChats recipient = service.getUserByEmail(chatRoom.getRecipientId());
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
    }

    @PostMapping("save_message")
    public String saveMessage(@RequestBody ChatMessage chatMessage) {
        String chatId = generateChatId(chatMessage.getSenderId(),
                chatMessage.getRecipientId());
        ChatRoom chatRoom = chatRoomService.findChatById(chatId);
        updateChatRoomMessage(chatRoom, chatMessage);

        return "Message successfully saved!";
    }

    @GetMapping("/fetchAllChats/{email}")
    public Stream<ChatRoom> findChatsForUser(@PathVariable String email) {
        return chatRoomService.findAllChats(email);
    }


    // Additional methods
    void updateUserChatRoom(UserChats userChats, ChatRoom chatRoom) {
        List<ChatRoom> chatRoomList = userChats.getChatRoomList();
        chatRoomList.add(chatRoom);
        userChats.setChatRoomList(chatRoomList);
        service.updateUser(userChats);
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
