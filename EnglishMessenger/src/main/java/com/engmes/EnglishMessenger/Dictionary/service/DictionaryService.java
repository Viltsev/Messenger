package com.engmes.EnglishMessenger.Dictionary.service;

import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Interests.model.Interest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DictionaryService {
    ResponseEntity getFindWord(String searchWord);

    void saveWord(Word word);

    void scrapeDictionary();
}
