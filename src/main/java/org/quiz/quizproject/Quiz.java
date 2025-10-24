package org.quiz.quizproject;

public class Quiz {
    private static final Quiz quiz = new Quiz();

    private String id;
    private String title;
    private String description;

    private int timeLimit; // Time limit in minutes. If <= 0, no time limit
    private boolean mixQuestions; // Mix questions randomly if true
    private int minScore; // If <= 0, no minimal passing score
    private boolean showResults; // If false, the results of quiz will not be shown to participants (thou it still will be saved to the json results file)
    private boolean answerRequired; // If true, the questions can not be left unanswered

    private Question[] questions;

    private Quiz(){

    }

    public static Quiz getInstance(){
        return quiz;
    }
}
