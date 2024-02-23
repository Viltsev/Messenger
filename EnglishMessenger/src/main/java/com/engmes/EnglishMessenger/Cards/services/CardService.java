package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;

public interface CardService {
    String saveCard(Card card);
    String saveToRepeatCard(Card card);
    String saveToLearnedCard(Card card);
}
