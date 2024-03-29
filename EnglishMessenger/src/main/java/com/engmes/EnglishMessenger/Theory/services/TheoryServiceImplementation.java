package com.engmes.EnglishMessenger.Theory.services;

import com.engmes.EnglishMessenger.Theory.models.*;
import com.engmes.EnglishMessenger.Theory.repo.GrammarTheoryRepository;
import com.engmes.EnglishMessenger.Theory.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheoryServiceImplementation implements TheoryService {
    private final Scraper scraper;
    private final GrammarTheoryRepository repository;

    @Override
    public GrammarTheory scrapeTheory() throws IOException {
        GrammarTheory grammarTheory = new GrammarTheory();
        List<Category> categoryList = scraper.scrapeCategories("https://preply.com/en/learn/english/grammar");
        grammarTheory.setCategories(categoryList);
        repository.save(grammarTheory);
        return grammarTheory;
    }

    @Override
    public GrammarTheory scrapeSortedTheory(String level) {
        GrammarTheory grammarTheory = getTheory();
        GrammarTheory sortedTheory = new GrammarTheory();

        List<Category> categories = grammarTheory.getCategories();
        List<Category> sortedCategories = new ArrayList<>();

        categories.forEach(category -> {
            Category sortedCategory = new Category();
            List<Topic> sortedTopicList = new ArrayList<>();

            List<Topic> topics = category.getTopics();
            topics.forEach(topic -> {
                Topic sortedTopic = new Topic();
                List<Subtopic> sortedSubtopicList = new ArrayList<>();

                if (!topic.getSubtopicList().isEmpty()) {
                    List<Subtopic> subtopicList = topic.getSubtopicList();

                    subtopicList.forEach(subtopic -> {
                        Subtopic sortedSubtopic = new Subtopic();
                        List<Theory> theoryList = subtopic
                                .getTheoryList()
                                .stream()
                                .filter(theory -> Objects.equals(theory.getLevel(), level))
                                .toList();

                        // create sorted subtopic
                        sortedSubtopic.setId(subtopic.getId());
                        sortedSubtopic.setTitle(subtopic.getTitle());
                        sortedSubtopic.setDescription(subtopic.getDescription());
                        sortedSubtopic.setTheoryList(theoryList);
                        sortedSubtopicList.add(sortedSubtopic);
                    });
                } else {
                    List<Theory> theoryList = topic
                            .getTheoryList()
                            .stream()
                            .filter(theory -> Objects.equals(theory.getLevel(), level))
                            .toList();
                    // create sorted topic
                    sortedTopic.setTheoryList(theoryList);
                    sortedTopic.setId(topic.getId());
                    sortedTopic.setTitle(topic.getTitle());
                    sortedTopic.setDescription(topic.getDescription());
                    sortedTopic.setSubtopicList(sortedSubtopicList);
                }
                if (!sortedTopic.getTheoryList().isEmpty() || !sortedTopic.getSubtopicList().isEmpty()) {
                    sortedTopicList.add(sortedTopic);
                }
            });
            // create sorted category
            sortedCategory.setId(category.getId());
            sortedCategory.setTitle(category.getTitle());
            sortedCategory.setDescription(category.getDescription());
            sortedCategory.setTopics(sortedTopicList);
            sortedCategories.add(sortedCategory);
        });

        sortedTheory.setId(grammarTheory.getId());
        sortedTheory.setCategories(sortedCategories);

        return sortedTheory;
    }

    @Override
    public GrammarTheory getTheory() {
        if (repository.findAll().stream().findFirst().isPresent()) {
            return repository.findAll().stream().findFirst().get();
        } else {
            return null;
        }
    }
}
