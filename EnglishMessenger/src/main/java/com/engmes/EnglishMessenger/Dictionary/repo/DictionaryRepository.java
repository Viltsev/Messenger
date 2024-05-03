package com.engmes.EnglishMessenger.Dictionary.repo;

import com.engmes.EnglishMessenger.Dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w WHERE w.word = ?1")
    List<Word> findEngWord(String searchWord);

    @Query("SELECT w FROM Word w WHERE w.description LIKE %?1%")
    List<Word> findRusWord(String searchWord);
}
