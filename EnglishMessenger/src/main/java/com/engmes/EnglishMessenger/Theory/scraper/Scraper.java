package com.engmes.EnglishMessenger.Theory.scraper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class Scraper {
    //private static final Logger logger = LoggerFactory.getLogger(Scraper.class);
    public void scrapeData() throws IOException {
        String mainUrl = "https://preply.com/en/learn/english/grammar/adjectives";
        Document doc = Jsoup
                .connect(mainUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

//        Elements topicsElements = doc.select("#primary .pill a");
//        List<String> links = new ArrayList<>();
//
//        for (Element listItem : topicsElements) {
//            links.add(listItem.attr("href"));
//        }
//
//        for (String link : links) {
//            System.out.println(link);
//        }

    }
}
