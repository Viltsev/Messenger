package com.engmes.EnglishMessenger.Chats.repo;

import com.engmes.EnglishMessenger.Chats.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);
    User findUserByEmail(String email);
}
