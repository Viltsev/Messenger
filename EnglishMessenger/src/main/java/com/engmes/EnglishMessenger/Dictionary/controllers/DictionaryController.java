package com.engmes.EnglishMessenger.Dictionary.controllers;


import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Dictionary.service.DictionaryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dictionary")
@AllArgsConstructor
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private final DictionaryService dictionaryService;

    @PostMapping("/scrape_dictionary")
    public void getWords() throws IOException {
        dictionaryService.scrapeDictionary();
    }

    @GetMapping("/findEngWord")
    public ResponseEntity findEngWord(@RequestParam String searchWord) {
        return dictionaryService.getFindEngWord(searchWord);
    }

    @GetMapping("/findRusWord")
    public ResponseEntity findRusWord(@RequestParam String searchWord) {
        return dictionaryService.getFindRusWord(searchWord);
    }

    @PostMapping("/saveWords")
    public void saveWord(@PathVariable Word word) {
       dictionaryService.saveWord(word);
    }
}
