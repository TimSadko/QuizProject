package org.quiz.quizproject.models;

import org.quiz.quizproject.models.AttemptResult;

import java.util.ArrayList;

public class QuizResult {
    private String quizId;
    private String name;

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

    public ArrayList<AttemptResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<AttemptResult> results) {
        this.results = results;
    }
}
