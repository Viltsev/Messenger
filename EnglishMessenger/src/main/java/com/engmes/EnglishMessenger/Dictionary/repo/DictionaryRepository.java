package com.engmes.EnglishMessenger.Dictionary.repo;

import com.engmes.EnglishMessenger.Dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w WHERE w.word = ?1")
    Word findWord(String searchWord);
}
