package com.engmes.EnglishMessenger.Theory.services;

import com.engmes.EnglishMessenger.Theory.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TheoryServiceImplementation implements TheoryService {
    private final Scraper scraper;

    @Override
    public void scrapeTheory() throws IOException {
        scraper.scrapeData();
    }
}
