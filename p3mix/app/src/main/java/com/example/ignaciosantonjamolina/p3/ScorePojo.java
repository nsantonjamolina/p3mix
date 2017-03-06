package com.example.ignaciosantonjamolina.p3;

/**
 * Created by ignaciosantonjamolina on 6/3/17.
 */

public final class ScorePojo {


        private Scores[] scores;

        public Scores[] getScores ()
        {
            return scores;
        }

        public void setScores (Scores[] scores)
        {
            this.scores = scores;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [scores = "+scores+"]";
        }
    }


