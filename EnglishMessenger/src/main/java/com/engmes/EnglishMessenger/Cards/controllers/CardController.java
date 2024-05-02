package com.engmes.EnglishMessenger.Cards.controllers;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;
import com.engmes.EnglishMessenger.Cards.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/refresh_set/{id}/{email}")
    public String  refreshCards(@PathVariable Long id, @PathVariable String email) {
        return service.refreshCards(id, email);
    }

    @DeleteMapping("/remove_all_total/{email}")
    public String refreshAllTotal(@PathVariable String email) { return service.deleteAllTotal(email); }

    @DeleteMapping("/delete_card")
    public String deleteCard(@RequestBody Card card) { return service.deleteCard(card); }

    @DeleteMapping("/delete_set/{id}/{email}")
    public String deleteSet(@PathVariable Long id, @PathVariable String email) {
        return service.deleteSet(email, id);
    }

    @GetMapping("/get_all_to_learn/{email}")
    public ResponseEntity getTotalToLearn(@PathVariable String email) {
        List<Card> cards = service.getTotalToLearn(email);
        if (cards != null) {
            return ResponseEntity.ok(cards);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_all_learned/{email}")
    public ResponseEntity getTotalLearned(@PathVariable String email) {
        List<Card> cards = service.getTotalLearned(email);
        if (cards != null) {
            return ResponseEntity.ok(cards);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_all_card_sets/{email}")
    public ResponseEntity getAllCardSets(@PathVariable String email) {
        List<CardSet> cardSets = service.getCardSets(email);
        if (cardSets != null) {
            return ResponseEntity.ok(cardSets);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_card_set/{email}/{id}")
    public ResponseEntity getCardSet(@PathVariable String email, @PathVariable Long id) {
        CardSet cardSet = service.getCardSet(email, id);

        if (cardSet != null) {
            return ResponseEntity.ok(cardSet);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
