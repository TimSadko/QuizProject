package org.quiz.quizproject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Quiz {
    private String quizId;
    private String title;
    private String description;

    private Boolean showResults; // If false, the results of quiz will not be shown to participants (though it still will be saved to the json results file)

    private Boolean allowRetry;
    private Integer randomQuestionCount;

    private ArrayList<QuestionWrapper> pages;

    public Quiz(){

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


    public boolean isShowResults() {
        return showResults == null ? true : showResults;
    }

    public void setShowResults(boolean showResults) {
        this.showResults = showResults;
    }

    public ArrayList<QuestionWrapper> getPages() {
        return pages;
    }

    public boolean isAllowRetry() {
        return allowRetry == null ? false : allowRetry;
    }

    public int getRandomQuestionCount() {
        return randomQuestionCount == null ? 0 : randomQuestionCount;
    }

    public void setAllowRetry(boolean allowRetry) {
        this.allowRetry = allowRetry;
    }

    public void setRandomQuestionCount(int randomQuestionCount) {
        this.randomQuestionCount = randomQuestionCount;
    }

    public void setPages(ArrayList<QuestionWrapper> pages) {
        this.pages = pages;
    }

    /*private int timeLimit; // Time limit in minutes. If <= 0, no time limit // Outdated
    private boolean mixQuestions; // Mix questions randomly if true
    private int minScore; // If <= 0, no minimal passing score
    private boolean answerRequired; // If true, the questions can not be left unanswered

        public int getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
        }

        public boolean isAnswerRequired() {
        return answerRequired;
    }

    public void setAnswerRequired(boolean answerRequired) {
        this.answerRequired = answerRequired;
    }
    public boolean isMixQuestions() {
        return mixQuestions;
    }

    public void setMixQuestions(boolean mixQuestions) {
        this.mixQuestions = mixQuestions;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

     */
}