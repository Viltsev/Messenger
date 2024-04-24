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
    public String createCardSet(@RequestBody CardSet cardSet) {
        return service.createCardSet(cardSet);
    }

    @PostMapping("/createCard")
    public String createCard(@RequestBody Card card) { return service.createCard(card); }

    @PostMapping("/save_to_learned")
    public String saveToLearned(@RequestBody Card card) { return service.saveToLearnedCards(card); }

    @PostMapping("/refresh_set")
    public String  refreshCards(@RequestParam Long id, @RequestParam String email) {
        return service.refreshCards(id, email);
    }

    @DeleteMapping("/remove_all_total/{email}")
    public String refreshAllTotal(@PathVariable String email) { return service.deleteAllTotal(email); }

    @DeleteMapping("/delete_card")
    public String deleteCard(@RequestBody Card card) { return service.deleteCard(card); }

    @DeleteMapping("/delete_set")
    public String deleteSet(@RequestParam Long id, @RequestParam String email) {
        return service.deleteSet(email, id);
    }
}
