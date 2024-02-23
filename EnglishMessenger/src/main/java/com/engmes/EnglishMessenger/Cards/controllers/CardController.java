package com.engmes.EnglishMessenger.Cards.controllers;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
public class CardController {
    private final CardService service;

    @PostMapping("/createCard")
    public String saveCard(@RequestBody Card card) {
        return service.saveCard(card);
    }

    @PostMapping("/save_to_repeat")
    public String saveToRepeat(@RequestBody Card card) {
        return service.saveToRepeatCard(card);
    }

    @PostMapping("/save_to_learned")
    public String saveToLearned(@RequestBody Card card) {
        return service.saveToLearnedCard(card);
    }
}
