package com.engmes.EnglishMessenger.Theory.controllers;

import com.engmes.EnglishMessenger.Theory.services.TheoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/theory")
@RequiredArgsConstructor
public class TheoryController {
    private final TheoryService service;
    @PostMapping("/save_topic")
    public String getSentencesExercises() throws IOException {
        service.scrapeTheory();
        return "Scrape successfully!";
    }
}
