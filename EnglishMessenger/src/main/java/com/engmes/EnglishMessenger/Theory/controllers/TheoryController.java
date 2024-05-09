package com.engmes.EnglishMessenger.Theory.controllers;

import com.engmes.EnglishMessenger.Theory.models.Category;
import com.engmes.EnglishMessenger.Theory.models.GrammarTheory;
import com.engmes.EnglishMessenger.Theory.services.TheoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/theory")
@RequiredArgsConstructor
public class TheoryController {
    private final TheoryService service;
    @PostMapping("/scrape_theory")
    public GrammarTheory getSentencesExercises() throws IOException {
        return service.scrapeTheory();
    }

    @GetMapping("/get_by_level/{level}")
    public GrammarTheory getTheoryByLevel(@PathVariable String level) {
        return service.scrapeSortedTheory(level);
    }

    @GetMapping("/get_theory")
    public GrammarTheory getTheory() {
        return service.getTheory();
    }

    @PostMapping("/save_theory")
    public String saveTheory(@RequestBody GrammarTheory theory) throws IOException {
        return service.saveTheory(theory);
    }
}
