package com.ozcelik.Utility;


import com.ozcelik.APIUtility.JsonBodyHandler;
import com.ozcelik.beans.Cell;
import com.ozcelik.beans.Word;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.ExecutionException;

public class HelperMethods {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    // for ASCII numbers for Alphabet
    public static final int MIN = 65;
    public static final int MAX = 90;
    private int row;
    private int column;

    public HelperMethods() {
    }
    public HelperMethods(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public String GetVocabularies(int type) throws InterruptedException, ExecutionException {

        String wordType = " ";
        switch (type){
            case 1:
                wordType = "";
                break;
            case 2:
                wordType = "noun";
                break;
            case 3:
                wordType = "verb";
                break;
            case 4:
                wordType = "adjective";
                break;
            case 5:
                wordType = "adverb";
                break;
            default:
                wordType = "";
                break;

        }
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
                        URI.create("https://api.api-ninjas.com/v1/randomword?type="+wordType))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        var responseFuture = client.sendAsync(request, new JsonBodyHandler<>(Word.class));

        // We can do other things here while the request is in-flight

        // This blocks until the request is complete
        var response = responseFuture.get();

        // the response:
        return response.body().get().word;
    }

    public void initGrid(Cell[][] grid) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = new Cell();

                grid[i][j].setLetter(generateLetter(MIN, MAX));
            }
        }
    }

    public void fillGridWithVocabularies(Cell[][] grid, String[] vocabularies) {
        int x = 0, y = 0;
        boolean isPlaced = false;

        for (int dex = 0; dex < vocabularies.length; dex++) {

            while (!isPlaced) {
                x = generateNumber(0, row - 1);
                y = generateNumber(0, column - 1);
                int vector = generateNumber(0, 7);

                if (isFit(grid, x, y, vector, vocabularies[dex], row, column)) {
                    placeWordToGrid(grid, x, y, vector, vocabularies[dex]);
                    isPlaced = true;
                }
            }
            isPlaced = false;
        }

    }

    public void printGrid(Cell[][] grid) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((grid[i][j].isFilled())) {
                    System.out.print(ANSI_RED + grid[i][j].getLetter() + " " + ANSI_RESET);
                } else {
                    System.out.print(grid[i][j].getLetter() + " ");
                }
            }
            System.out.println();
        }
    }

    public char generateLetter(int min, int max) {
        return (char) ((Math.random() * (max - min)) + min);
    }

    public int generateNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public boolean checkAllPlace(Cell[][] grid, int i, int j, int k, String vocab) {
        if (grid[j][k].isFilled()) {
            if (grid[j][k].getLetter() != vocab.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFit(Cell[][] grid, int x, int y, int vector, String vocab, int row, int column) {
        boolean isCompleted = true;
        switch (vector) {
            case 0:
                if ((x - vocab.length()) >= 0) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 1:
                if ((x - vocab.length()) >= 0 && (y + vocab.length() < column)) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--, k++) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;

            case 2:
                if (column > (y + vocab.length())) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, k++) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 3:
                if (row > (x + vocab.length()) && (y + vocab.length() < column)) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++, k++) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 4:
                if (row > x + vocab.length()) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 5:
                if (row > (x + vocab.length()) && (y - vocab.length() >= 0)) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++, k--) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 6:
                if (y - vocab.length() >= 0) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, k--) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            case 7:
                if ((x - vocab.length()) >= 0 && (y - vocab.length() >= 0)) {
                    for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--, k--) {
                        isCompleted = checkAllPlace(grid, i, j, k, vocab);
                        if (!isCompleted) break;
                    }
                } else return false;
                return isCompleted;
            default:
                return false;
        }
    }

    public void placeWordToGrid(Cell[][] grid, int x, int y, int vector, String vocab) {

        switch (vector) {
            case 0:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 1:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--, k++) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 2:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, k++) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 3:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++, k++) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 4:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 5:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j++, k--) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 6:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, k--) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
            case 7:
                for (int i = 0, j = x, k = y; i < vocab.length(); i++, j--, k--) {
                    grid[j][k].setLetter(vocab.charAt(i));
                    grid[j][k].setFilled(true);
                }
                break;
        }
    }

    public boolean isInt(String input, int min, int max) {
        int value = 0;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        if(value < min || value > max) return false;

        return true;
    }
}
