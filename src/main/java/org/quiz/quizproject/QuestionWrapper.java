package org.quiz.quizproject;

public class QuestionWrapper {
    private Question[] elements;
    private int timeLimit;

    public Question getQuestion(){
        return elements[0];
    }

    public int GetTimeLimit(){
        return timeLimit;
    }

    public void setElements(Question[] elements) {
        this.elements = elements;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
