package com.engmes.EnglishMessenger.Cards.repo;

import com.engmes.EnglishMessenger.Cards.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
