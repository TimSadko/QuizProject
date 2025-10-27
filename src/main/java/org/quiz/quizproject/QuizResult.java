package org.quiz.quizproject;

import java.util.ArrayList;

public class QuizResult {
    private String quizId;
    private String name;
    private int maxPoints;

    private ArrayList<AttemptResult> results;

    public QuizResult() {}

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public ArrayList<AttemptResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<AttemptResult> results) {
        this.results = results;
    }
}
