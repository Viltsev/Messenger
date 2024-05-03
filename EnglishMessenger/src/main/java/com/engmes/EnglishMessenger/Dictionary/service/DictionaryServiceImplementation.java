package com.engmes.EnglishMessenger.Dictionary.service;

import com.engmes.EnglishMessenger.Dictionary.controllers.DictionaryController;
import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Dictionary.repo.DictionaryRepository;
import com.engmes.EnglishMessenger.Dictionary.scraper.Scraper;
import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Theory.models.Category;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class DictionaryServiceImplementation implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    private final DictionaryRepository dictionaryRepository;

    public ResponseEntity getFindEngWord(String searchWord) {
        List<Word> wordList = dictionaryRepository.findEngWord(searchWord);
        Optional<Word> word = wordList.stream().findFirst();
        if (word.isPresent()) {
            return ResponseEntity.ok(word);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity getFindRusWord(String searchWord) {
        List<Word> wordList = dictionaryRepository.findRusWord(searchWord);
        List<Word> sortedWords = wordList.stream()
                .sorted(Comparator.comparingInt(word -> word.getWord().length()))
                .toList();
        int numberOfBestMatches = 5;

        List<Word> bestMatches = sortedWords.stream()
                .limit(numberOfBestMatches)
                .collect(Collectors.toList());

        if (!bestMatches.isEmpty()) {
            return ResponseEntity.ok(bestMatches);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public void saveWord(Word word) {
        dictionaryRepository.save(word);
    }

    @Override
    public void scrapeDictionary() {
        Scraper scraper = new Scraper();
        List<Word> wordList = scraper.scraper("https://wooordhunt.ru/dic/content/en_ru");
        for (Word word : wordList) {
            saveWord(word);
        }
    }
}
