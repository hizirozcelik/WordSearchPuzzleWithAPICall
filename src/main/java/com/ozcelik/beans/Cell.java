package com.ozcelik.beans;

public class Cell {
    private int x;
    private int y;
    private char letter;
    private boolean isFilled;

    public Cell(){

    }
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        letter = 0;
        isFilled = false;
    }

    public Cell(char letter) {
        this.letter = letter;
        this.isFilled = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", letter=" + letter +
                ", isFilled=" + isFilled +
                '}';
    }
}
