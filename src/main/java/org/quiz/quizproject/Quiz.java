package org.quiz.quizproject;

public class Quiz {
    //private static final Quiz quiz = new Quiz();

    private String id;
    private String title;
    private String description;

    private int timeLimit; // Time limit in minutes. If <= 0, no time limit
    private boolean mixQuestions; // Mix questions randomly if true
    private int minScore; // If <= 0, no minimal passing score
    private boolean showResults; // If false, the results of quiz will not be shown to participants (thou it still will be saved to the json results file)
    private boolean answerRequired; // If true, the questions can not be left unanswered

    private Question[] questions;

    public Quiz(){

    }

//    public static Quiz getInstance(){
//        return quiz;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
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

    public boolean isShowResults() {
        return showResults;
    }

    public void setShowResults(boolean showResults) {
        this.showResults = showResults;
    }

    public boolean isAnswerRequired() {
        return answerRequired;
    }

    public void setAnswerRequired(boolean answerRequired) {
        this.answerRequired = answerRequired;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }
}