package com.engmes.EnglishMessenger.Theory.scraper;

import com.engmes.EnglishMessenger.Theory.models.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class Scraper {
    private static final Logger logger = LoggerFactory.getLogger(Scraper.class);

    public List<Category> scrapeCategories(String mainUrl) throws IOException {
        Document doc = Jsoup
                .connect(mainUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        Elements categoriesElements = doc.select(".styles_topicCardWrapper__yd3fU");

        // categories list
        List<Category> categories = new ArrayList<>();

        categoriesElements.forEach(categoryCard -> {
            Category category = new Category();

            Element link = categoryCard.selectFirst("a");
            String categoryHref = link.attr("href");
            String categoryTitle = link.text();

            Element description = categoryCard.selectFirst(".khPkeq._2Vx7ey._2JBCx7");
            String categoryDescription = description.text();

            category.setTitle(categoryTitle);
            category.setDescription(categoryDescription);

            // scrape topic (or theory if topic is null) list
            try {
                List<Topic> topicList = scrapeTopics(categoryHref);

                if (!topicList.isEmpty()) {
                    category.setTopics(topicList);
                } else {
                    // scrape theory
                    List<Theory> theoryList = scrapeTheory(categoryHref);
                    // setting theory list to category
                    category.setTheoryList(theoryList);
                }
                // set topic list to category
                // category.setTopics(topicList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            categories.add(category);
        });

        return categories;
    }

    public List<Topic> scrapeTopics(String categoryUrl) throws IOException {
        Document doc = Jsoup
                .connect(categoryUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        Elements topicsElements = doc.select(".styles_topicCardWrapper__yd3fU");

        // topics list
        List<Topic> topics = new ArrayList<>();

        topicsElements.forEach(topicCard -> {
            Topic topic = new Topic();

            Element link = topicCard.selectFirst("a");
            String topicHref = link.attr("href");
            String topicTitle = link.text();

            Element description = topicCard.selectFirst(".khPkeq._2Vx7ey._2JBCx7");
            String topicDescription = description.text();

            topic.setTitle(topicTitle);
            topic.setDescription(topicDescription);

            try {
                // get all subtopics
                List<Subtopic> subtopicList = scrapeSubtopics(topicHref);
                if (!subtopicList.isEmpty()) {
                    topic.setSubtopicList(subtopicList);
                } else {
                    // scrape theory
                    List<Theory> theoryList = scrapeTheory(topicHref);
                    // setting theory list to topic
                    topic.setTheoryList(theoryList);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // adding to topic list
            topics.add(topic);
        });

        return topics;
    }

    public List<Subtopic> scrapeSubtopics(String topicUrl) throws IOException {
        Document doc = Jsoup
                .connect(topicUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        Elements subtopicsElements = doc.select(".styles_topicCardWrapper__yd3fU");

        // subtopics list
        List<Subtopic> subtopics = new ArrayList<>();

        if (!subtopicsElements.isEmpty()) {
            subtopicsElements.forEach(subtopicCard -> {
                Subtopic subtopic = new Subtopic();

                Element link = subtopicCard.selectFirst("a");
                String subtopicHref = link.attr("href");
                String subtopicTitle = link.text();

                Element description = subtopicCard.selectFirst(".khPkeq._2Vx7ey._2JBCx7");
                String subtopicDescription = description.text();

                subtopic.setTitle(subtopicTitle);
                subtopic.setDescription(subtopicDescription);

                // scrape theory
                try {
                    List<Theory> theoryList = scrapeTheory(subtopicHref);
                    // setting theory list to subtopic
                    subtopic.setTheoryList(theoryList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                subtopics.add(subtopic);
            });
        }

        return subtopics;
    }

    public List<Theory> scrapeTheory(String theoryUrl) throws IOException {
//        String mainUrl = "https://preply.com/en/learn/english/grammar/affixes";
        Document doc = Jsoup
                .connect(theoryUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        Elements elements = doc.select("div._15uGWh.X4eHUe.TeMu3z._21X6IW._1pwa5v._23vtjf._1FM0S9._3-ZZug._2mguWZ._3aQPK_._2rnTIx._2QUxbW._1Wpc49");

        List<Theory> theoryList = new ArrayList<>();

        elements.forEach(element -> {
            Theory theory = new Theory();
            Element theoryTitleElement = element.selectFirst("h2.preply-ds-heading._3DJ88_.qtotyM._2JBCx7");

            // getting theory title
            String theoryTitle = theoryTitleElement.text();
            theory.setTitle(theoryTitle);

            // getting level
            String level = extractLevel(theoryTitleElement.text());
            theory.setLevel(level);

            // getting explanation
            Element explanationsElement = element.selectFirst("span");
            String explanation = explanationsElement
                    .html()
                    .replaceAll("<br>", "\n")
                    .replaceAll("\\<.*?\\>", "");
            theory.setExplanation(explanation);


            // getting examples and common mistakes
            Elements exampleElements = element.select("p.khPkeq._19BXdF._2JBCx7");
            Elements mistakesElements = element.select("p.khPkeq._19eGZB._2JBCx7");


            exampleElements.forEach(newElement -> {
                // getting examples
                if ("Example".equals(newElement.text())) {
                    Element nextElement = newElement.nextElementSibling();
                    String example = nextElement
                            .html()
                            .replaceAll("<br>", "\n")
                            .replaceAll("\\<.*?\\>", "");
                    theory.setExample(example);
                }

                // getting common mistakes
                if ("Common mistakes".equals(newElement.text())) {
                    Element nextElement = newElement.nextElementSibling();
                    String commonMistake = nextElement.text();
                    theory.setCommonMistakeDescription(commonMistake);

                    mistakesElements.forEach(mistake -> {
                        int index = mistakesElements.indexOf(mistake);
                        String mistakeText = mistake.text();
                        if (index == 0) {
                            theory.setCmWrong(mistakeText);
                        } else {
                            theory.setCmRight(mistakeText);
                        }
                    });
                }
            });

            theoryList.add(theory);
        });

        return theoryList;
    }

    private static String extractLevel(String text) {
        Pattern pattern = Pattern.compile("[A-Z]\\d$");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String level = matcher.group().substring(0, 1);

            String levelDescription;

            switch (level) {
                case "A":
                    levelDescription = "Beginners";
                    break;
                case "B":
                    levelDescription = "Intermediate";
                    break;
                case "C":
                    levelDescription = "Advanced";
                    break;
                default:
                    levelDescription = "Unknown";
                    break;
            }

            return levelDescription;
        } else {
            return "Unknown";
        }
    }
}
