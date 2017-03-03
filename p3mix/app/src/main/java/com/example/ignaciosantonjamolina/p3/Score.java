package com.example.ignaciosantonjamolina.p3;

/**
 * Created by ignaciosantonjamolina on 3/3/17.
 */

public class Score {
    int id;
    String name;
    int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
