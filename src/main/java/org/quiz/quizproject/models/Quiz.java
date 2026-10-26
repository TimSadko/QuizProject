package org.quiz.quizproject.models;

import org.quiz.quizproject.models.QuestionWrapper;

import java.util.ArrayList;

public class Quiz {
    private String quizId;
    private String title;
    private String description;

    private ArrayList<QuestionWrapper> pages;

    private String completedHtml;

    public Quiz(){

    }

    public int countCorrectQuestions() {
        int correct = 0;

        for (int i = 0; i < getPages().size(); i++){
            if(getPages().get(i).getQuestion().isAnswerCorrect()) correct++;
        }

        return correct;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<QuestionWrapper> getPages() {
        return pages;
    }

    public void setPages(ArrayList<QuestionWrapper> pages) {
        this.pages = pages;
    }

    public String getCompletedHtml() {
        return completedHtml;
    }

    public void setCompletedHtml(String completedHtml) {
        this.completedHtml = completedHtml;
    }
}