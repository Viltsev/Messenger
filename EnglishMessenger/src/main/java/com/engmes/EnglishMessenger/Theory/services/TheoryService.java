package com.engmes.EnglishMessenger.Theory.services;

import com.engmes.EnglishMessenger.Theory.models.Category;
import com.engmes.EnglishMessenger.Theory.models.GrammarTheory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TheoryService {
    GrammarTheory scrapeTheory() throws IOException;
    GrammarTheory scrapeSortedTheory(String level);
    GrammarTheory getTheory();
}
