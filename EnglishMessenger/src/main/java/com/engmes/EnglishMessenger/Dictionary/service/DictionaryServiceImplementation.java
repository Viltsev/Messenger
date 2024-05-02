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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class DictionaryServiceImplementation implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    private final DictionaryRepository dictionaryRepository;

    public Word getFindWord(String searchWord) {
        return dictionaryRepository.findWord(searchWord);
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
