package com.engmes.EnglishMessenger.Cards.controllers;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;
import com.engmes.EnglishMessenger.Cards.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
public class CardController {
    private final CardService service;

    @PostMapping("/createCardSet")
    public void createCardSet(@RequestBody CardSet cardSet) {
        service.createCardSet(cardSet);
    }

    @PostMapping("/createCard")
    public void createCard(@RequestBody Card card) {
        service.createCard(card);
    }

    @PostMapping("/save_to_learned")
    public void saveToLearned(@RequestBody Card card) {
        service.saveToLearnedCards(card);
    }

    @PostMapping("/refresh_set")
    public void refreshCards(@RequestParam Long id, @RequestParam String email) {
        service.refreshCards(id, email);
    }
}
