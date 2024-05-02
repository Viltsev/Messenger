package com.engmes.EnglishMessenger.Dictionary.service;

import com.engmes.EnglishMessenger.Dictionary.model.Word;
import com.engmes.EnglishMessenger.Interests.model.Interest;

import java.util.List;

public interface DictionaryService {
    Word getFindWord(String searchWord);

    void saveWord(Word word);
}
