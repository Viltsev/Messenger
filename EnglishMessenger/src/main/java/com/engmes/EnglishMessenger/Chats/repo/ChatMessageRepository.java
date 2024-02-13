package com.engmes.EnglishMessenger.Chats.repo;

import com.engmes.EnglishMessenger.Chats.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {}
