package com.engmes.EnglishMessenger.Grammar.service;

import com.engmes.EnglishMessenger.Grammar.model.GrammarModel;
import com.engmes.EnglishMessenger.Grammar.model.Match;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GrammarService {
    List<Match> getGrammarCorrections(String text) throws IOException, InterruptedException;
}
