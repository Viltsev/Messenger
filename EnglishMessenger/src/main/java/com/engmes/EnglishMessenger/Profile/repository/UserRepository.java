package com.engmes.EnglishMessenger.Profile.repository;

import com.engmes.EnglishMessenger.Profile.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
