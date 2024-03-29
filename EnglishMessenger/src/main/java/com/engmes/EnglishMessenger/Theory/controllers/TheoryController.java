package com.engmes.EnglishMessenger.Theory.controllers;

import com.engmes.EnglishMessenger.Theory.models.Category;
import com.engmes.EnglishMessenger.Theory.models.GrammarTheory;
import com.engmes.EnglishMessenger.Theory.services.TheoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/theory")
@RequiredArgsConstructor
public class TheoryController {
    private final TheoryService service;
    @PostMapping("/save_topic")
    public GrammarTheory getSentencesExercises() throws IOException {
        //service.scrapeTheoryNew();
        return service.scrapeTheory();
    }
}
