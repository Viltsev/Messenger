package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;

public interface CardService {
    CardSet findCardSet(Long id, String email);
    String createCardSet(CardSet cardSet);
    void updateCardSet(CardSet cardSet);
    void updateTotalCards(String userEmail);
    String createCard(Card card);
    String saveToLearnedCards(Card card);
    String refreshCards(Long id, String email);
    String deleteAllTotal(String email);
    String deleteCard(Card card);
    String deleteSet(String email, Long id);
}
