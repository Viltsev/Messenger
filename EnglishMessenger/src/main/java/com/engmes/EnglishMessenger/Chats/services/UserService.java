package com.engmes.EnglishMessenger.Chats.services;

import com.engmes.EnglishMessenger.Chats.model.User;

import java.util.List;
import java.util.stream.Stream;

public interface UserService {
    Stream<User> findAllUsers(String email);
    User saveUser(User user);
    User findByEmail(String email);
    User getUserByEmail(String email);
    User updateUser(User user);
    void deleteUser(String email);
}
