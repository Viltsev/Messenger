package com.engmes.EnglishMessenger.Chats.repo;

import com.engmes.EnglishMessenger.Chats.model.UserChats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserChats, Long> {
    void deleteByEmail(String email);
    UserChats findUserByEmail(String email);
}
