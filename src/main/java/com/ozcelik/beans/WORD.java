package com.ozcelik.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WORD {
    public final String word;

    public WORD(@JsonProperty("word") String word) {
        this.word = word;
    }
}