package com.engmes.EnglishMessenger.Grammar.controllers;

import com.engmes.EnglishMessenger.Grammar.model.Match;
import com.engmes.EnglishMessenger.Grammar.service.GrammarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/checkGrammar")
@AllArgsConstructor
public class GrammarController {
    private final GrammarService service;

    @PostMapping
    public List<Match> checkGrammar(@RequestBody String text) throws IOException, InterruptedException {
        List<Match> matches = service.getGrammarCorrections(text);
        return matches;
    }
}
