package com.engmes.EnglishMessenger.Grammar.model;

import lombok.Data;

import java.util.List;

@Data
public class Match {
    private String message;
    private List<Replacement> replacements;
    private int offset;
    private int length;
    private Rule rule;
}
