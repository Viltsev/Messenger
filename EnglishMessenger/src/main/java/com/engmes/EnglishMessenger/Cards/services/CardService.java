package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;

public interface CardService {
    CardSet findCardSet(Long id, String email);
    void createCardSet(CardSet cardSet);
    void updateCardSet(CardSet cardSet);
    void createCard(Card card);
    void saveToLearnedCards(Card card);
    void refreshCards(Long id, String email);
}
