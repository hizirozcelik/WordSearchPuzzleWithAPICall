package com.ozcelik.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Word {
    public final String word;

    public Word(@JsonProperty("word") String word) {
        this.word = word;
    }
}