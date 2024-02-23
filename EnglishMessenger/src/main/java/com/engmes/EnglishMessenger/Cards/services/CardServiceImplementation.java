package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardLists;
import com.engmes.EnglishMessenger.Cards.repo.CardRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class CardServiceImplementation implements CardService {
    private final CardRepository repository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImplementation.class);
    @Override
    public String saveCard(Card card) {
        repository.save(card);
        return userService.saveCard(card);
    }

    @Override
    public String saveToRepeatCard(Card card) {
        return userService.saveCardTo(CardLists.SAVE_TO_REPEAT, card);
    }

    @Override
    public String saveToLearnedCard(Card card) {
        return userService.saveCardTo(CardLists.SAVE_TO_LEARNED, card);
    }
}
