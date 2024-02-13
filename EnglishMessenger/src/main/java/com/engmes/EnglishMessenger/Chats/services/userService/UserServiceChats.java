package com.engmes.EnglishMessenger.Chats.services.userService;

import com.engmes.EnglishMessenger.Chats.model.UserChats;

import java.util.stream.Stream;

public interface UserServiceChats {
    Stream<UserChats> findAllUsers(String email);
    UserChats saveUser(UserChats userChats);
    UserChats findByEmail(String email);
    UserChats getUserByEmail(String email);
    UserChats updateUser(UserChats userChats);
    void deleteUser(String email);
}
