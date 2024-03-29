package com.engmes.EnglishMessenger.Theory.services;

import com.engmes.EnglishMessenger.Theory.models.Category;
import com.engmes.EnglishMessenger.Theory.models.GrammarTheory;
import com.engmes.EnglishMessenger.Theory.repo.GrammarTheoryRepository;
import com.engmes.EnglishMessenger.Theory.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheoryServiceImplementation implements TheoryService {
    private final Scraper scraper;
    private final GrammarTheoryRepository repository;

    @Override
    public GrammarTheory scrapeTheory() throws IOException {
        GrammarTheory grammarTheory = new GrammarTheory();
        List<Category> categoryList = scraper.scrapeCategories("https://preply.com/en/learn/english/grammar/tenses");
        grammarTheory.setCategories(categoryList);
        repository.save(grammarTheory);
        return grammarTheory;
    }
}
