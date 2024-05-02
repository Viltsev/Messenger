package com.engmes.EnglishMessenger.Chats.controllers;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import com.engmes.EnglishMessenger.Chats.services.chatMessageService.ChatMessageService;
import com.engmes.EnglishMessenger.Chats.services.chatRoomService.ChatRoomImplementation;
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
    private final ChatMessageService chatMessageService;

    @PostMapping("create_chatroom")
    public String saveChat(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @PostMapping("save_message")
    public String saveMessage(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.saveMessage(chatMessage);
    }

    @GetMapping("/get_chat_messages/{chatId}")
    public List<ChatMessage> getChatMessages(@PathVariable String chatId) {
        ChatRoom chatRoom = chatRoomService.findChatById(chatId);
        return chatRoom.getChatMessageList();
    }

    @GetMapping("/fetchAllChats/{email}")
    public Stream<ChatRoom> findChatsForUser(@PathVariable String email) {
        return chatRoomService.findAllChats(email);
    }

    @GetMapping("fetch_all_chat_users/{email}")
    public List<User> fetchAllChatUsers(@PathVariable String email) {
        return chatRoomService.getChatUsers(email);
    }

    @GetMapping("/delete_chat/{chatId}")
    public String deleteChat(@PathVariable String chatId) {
        return chatRoomService.deleteChat(chatId);
    }
}
