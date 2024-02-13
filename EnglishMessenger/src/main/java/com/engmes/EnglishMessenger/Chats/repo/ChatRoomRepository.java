package com.engmes.EnglishMessenger.Chats.repo;

import com.engmes.EnglishMessenger.Chats.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}
