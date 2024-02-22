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

    @PostMapping("/saveCard")
    public void saveCard(@RequestBody Card card) {
        service.saveCard(card);
    }
}
