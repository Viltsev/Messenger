package com.engmes.EnglishMessenger.Dictionary.controllers;


import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Dictionary.service.DictionaryService;
import com.engmes.EnglishMessenger.Interests.model.Interest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
@AllArgsConstructor
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    @Autowired
    private final DictionaryService dictionaryService;

    @GetMapping("/findWord")
    public Word findWord(@RequestParam String searchWord) {
        return dictionaryService.getFindWord(searchWord);
    }

    @PostMapping("/saveWords")
    public void saveWord(@RequestBody List<Word> wordList) {
       wordList.forEach(dictionaryService::saveWord);
    }
}
