package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.repo.CardRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class CardServiceImplementation implements CardService {
    private final CardRepository repository;
    private final UserService userService;
    @Override
    public void saveCard(Card card) {
        repository.save(card);
        userService.saveCard(card);
    }
}
