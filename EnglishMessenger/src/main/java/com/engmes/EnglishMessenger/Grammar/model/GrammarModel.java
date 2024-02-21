package com.engmes.EnglishMessenger.Grammar.model;

import lombok.Data;

import java.util.List;

@Data
public class GrammarModel {
    private List<Match> matches;
}

@Data
class Replacement {
    private String value;
}

@Data
class Rule {
    private String description;
    private List<Url> urls;
}

@Data
class Url {
    private String value;
}


