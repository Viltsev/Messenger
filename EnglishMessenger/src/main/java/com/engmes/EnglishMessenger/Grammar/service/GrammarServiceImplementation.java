package com.engmes.EnglishMessenger.Grammar.service;

import com.engmes.EnglishMessenger.Grammar.model.GrammarModel;
import com.engmes.EnglishMessenger.Grammar.model.Match;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Primary
public class GrammarServiceImplementation implements GrammarService{
    @Override
    public List<Match> getGrammarCorrections(String text) throws IOException, InterruptedException {
        String apiURL = "https://dnaber-languagetool.p.rapidapi.com/v2/check";
        String apiKey = "cd02c58415mshb53743187d9ff8ap1c314fjsn07806753884e";
        String body = "language=en-US&text=" + text;
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "dnaber-languagetool.p.rapidapi.com")
                .POST(bodyPublisher)
                .build();
        HttpResponse<String> response =
                HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        GrammarModel grammarModel = gson.fromJson(response.body(), GrammarModel.class);
        List<Match> matches = grammarModel.getMatches();
        return matches;
    }
}
