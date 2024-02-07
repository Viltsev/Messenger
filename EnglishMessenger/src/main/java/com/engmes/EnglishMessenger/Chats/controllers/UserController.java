package com.engmes.EnglishMessenger.Chats.controllers;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.model.User;
import com.engmes.EnglishMessenger.Chats.services.ChatRoomService;
import com.engmes.EnglishMessenger.Chats.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final ChatRoomService chatRoomService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/fetchAllUsers/{email}")
    public Stream<User> findAllStudent(@PathVariable String email) {
        // todo
        return service.findAllUsers(email);
    }

    @PostMapping("register")
    public String saveUser(@RequestBody User user) {
        service.saveUser(user);
        return "User successfully saved!";
    }

    @GetMapping("/authByEmail/{email}")
    public User findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PutMapping("update_user")
    public User updateStudent(User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("delete_user/{email}")
    public void deleteStudent(@PathVariable String email) {
        service.deleteUser(email);
    }

    @PostMapping("save_chatroom")
    public String saveChat(@RequestBody ChatRoom chatRoom) {

        // sender update
        User sender = service.getUserByEmail(chatRoom.getSenderId());
        ChatRoom senderChatRoom = chatRoom;
        updateUserChatRoom(sender, senderChatRoom);

        // recipient update
        User recipient = service.getUserByEmail(chatRoom.getRecipientId());
        ChatRoom recipientChatRoom = chatRoom;
        updateUserChatRoom(recipient, recipientChatRoom);

        return "Chat successfully saved!";
    }

    @PostMapping("save_message")
    public String saveMessage(@RequestBody ChatMessage chatMessage) {

        // todo: find chat by chatId
        // ChatRoom chatRoom

        return "Message successfully saved!";
    }

    private void updateUserChatRoom(User user, ChatRoom chatRoom) {
        List<ChatRoom> chatRoomList = user.getChatRoomList();
        chatRoomList.add(chatRoom);
        user.setChatRoomList(chatRoomList);
        service.updateUser(user);
        chatRoomService.saveChat(chatRoom);
    }

    @GetMapping("/fetchAllChats/{email}")
    public Stream<ChatRoom> findChatsForUser(@PathVariable String email) {
        return chatRoomService.findAllChats(email);
    }

}
