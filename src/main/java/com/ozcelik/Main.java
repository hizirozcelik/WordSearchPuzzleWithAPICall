package com.ozcelik;

import com.ozcelik.Utility.HelperMethods;
import com.ozcelik.beans.Cell;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/*
This application generate a word search puzzle with random words. Words get from https://api-ninjas.com/ with an API call.
I use Jackson as a Java JSON library for this API calls. https://github.com/FasterXML/jackson
I implement API calls to my project based on this https://www.twilio.com/blog/5-ways-to-make-http-requests-in-java
@author Hizir Ozcelik
@version 1.2
@date November 7, 2022 @ Oakville
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // parameters with default values
        int row = 30;
        int column = 40;
        int numberOfWord = 40;
        int difficultyLevel = 2;
        int wordType = 1;
        HelperMethods checkInput = new HelperMethods();

        System.out.println("Search Word Puzzle Generator v1.2");

        // User inputs for difficulty level
        Scanner sc = new Scanner(System.in);
        System.out.println("What would be the puzzle difficulty level");
        System.out.println("Please enter <1> for basic <20x20>");
        System.out.println("Please enter <2> for medium <30x40>");
        System.out.println("Please enter <3> for advanced <40x60>");
        String userInput = sc.nextLine();

        // user input validation based on menu items and valid integers entry
        if (checkInput.isInt(userInput, 1, 3)) {
            difficultyLevel = Integer.parseInt(userInput);
        } else {
            System.out.println("Something went wrong. your puzzle generating at medium level.");
        }

        // Define grid dimensions and number of words for the puzzle based on difficulty.
        // Default values already define as medium level
        if (difficultyLevel == 1) {
            numberOfWord = 15;
            row = 20;
            column = 20;
        }
        if (difficultyLevel == 3) {
            numberOfWord = 60;
            row = 40;
            column = 60;
        }

        // User inputs for types of word
        System.out.println("Please select the words' types for the puzzle");
        System.out.println("Please enter <1> for mixed");
        System.out.println("Please enter <2> for nouns>");
        System.out.println("Please enter <3> for verbs>");
        System.out.println("Please enter <4> for adjectives>");
        System.out.println("Please enter <5> for adverbs>");
        userInput = sc.nextLine();

        // user input validation
        if (checkInput.isInt(userInput, 1, 5)) {
            wordType = Integer.parseInt(userInput);
        } else {
            System.out.println("Something went wrong. your puzzle generating with mixed words.");
        }

        // Creating empty puzzle grid
        Cell[][] grid = new Cell[row][column];

        // Helper methods instance re-creation with row and column
        HelperMethods utility = new HelperMethods(row, column);

        // Creating vocabularies array with API call
        String[] vocabularies = new String[numberOfWord];
        for (int i = 0; i < numberOfWord; i++) {
            if (difficultyLevel == 1) {
                String vocab = utility.GetVocabularies(wordType).toUpperCase();
                int numOfChar = vocab.length();
                if (numOfChar <= 8) {
                    vocabularies[i] = vocab;
                } else i--;
            } else vocabularies[i] = utility.GetVocabularies(wordType).toUpperCase();
        }
        // Initialize puzzle grid
        utility.initGrid(grid);
        // creating puzzle with words
        utility.fillGridWithVocabularies(grid, vocabularies);
        // printing puzzle grid
        utility.printGrid(grid);
        // Printing words in order
        System.out.println("Vocabulary list for the puzzle");
        var sorted = Arrays.asList(vocabularies);
        sorted.sort(String::compareToIgnoreCase);
        int sno = 0;
        for (String item : sorted) {
            sno++;
            System.out.println(sno + ". " + item);
        }
        System.out.println("Enjoy solving the puzzle");
        System.out.println("Created by Hizir Ozcelik @Oakville");
    }
}
