package com.engmes.EnglishMessenger.Dictionary.service;

import com.engmes.EnglishMessenger.Dictionary.controllers.DictionaryController;
import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Dictionary.repo.DictionaryRepository;
import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
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
}
