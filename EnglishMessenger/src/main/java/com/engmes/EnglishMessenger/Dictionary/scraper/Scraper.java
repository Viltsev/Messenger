package com.engmes.EnglishMessenger.Dictionary.scraper;
import com.engmes.EnglishMessenger.Dictionary.model.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

public class Scraper {
    private static final Logger logger = LoggerFactory.getLogger(Scraper.class);

    List<Word> words = new ArrayList<>();

    private String fetch(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.html();
    }

    private Set<String> extractUrls(String html) {
        Set<String> urls = new HashSet<>();
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String text = link.text();
            if (text.trim().length() == 2) {
                String src = "https://wooordhunt.ru/" + link.attr("href");
                urls.add(src);
            }
        }
        return urls;
    }

    private void downloadPhrase(String url) throws IOException {
        String html = fetch(url);
        Document doc = Jsoup.parse(html);
        Elements paragraphs = doc.select("p");
        for (Element paragraph : paragraphs) {
            String word = paragraph.selectFirst("a").text();
            String text = paragraph.text();
            String[] parts = text.split("—");
            if (parts.length > 1) {
                String additionalText = parts[1].trim();
                Word data = new Word();
                data.setId(UUID.randomUUID().toString());
                data.setWord(word);
                data.setDescription(additionalText);

                words.add(data);
            }
        }
    }

    private void processPage(String url) {
        try {
            String html = fetch(url);
            Set<String> urls = extractUrls(html);
            for (String link : urls) {
                downloadPhrase(link);
            }
        } catch (IOException e) {
            logger.warn("Error processing page: " + url);
        }
    }

    public List<Word> scraper(String startUrl) {
        try {
            processPage(startUrl);
        } catch (Exception e) {
            logger.warn("Error crawling page: " + startUrl);
        }
        return words;
    }
}
