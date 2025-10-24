package org.quiz.quizproject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class MultipleChoiceQuestion extends Question {
    private String text;
    private final QuestionType type = QuestionType.MultipleChoice;
    private int points;

    private MultipleChoiceQuestionBehaviour questionBehaviour;

    private String[] answers;

    private String[] correctAnswers;
    private LinkedList<String> selectedAnswers;
    private boolean isAnswerSelected;

    public MultipleChoiceQuestion(){

    }

    public MultipleChoiceQuestionBehaviour getQuestionBehaviour() {
        return questionBehaviour;
    }

    public void setQuestionBehaviour(MultipleChoiceQuestionBehaviour questionBehaviour) {
        this.questionBehaviour = questionBehaviour;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public boolean getIsAnswerSelected() {
        return isAnswerSelected;
    }

    public void setIsAnswerSelected(boolean isAnswerSelected) {
        this.isAnswerSelected = isAnswerSelected;
    }

    public LinkedList<String> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(LinkedList<String> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public int calcPoints() {
        if(questionBehaviour == MultipleChoiceQuestionBehaviour.StrictAll){
            if(correctAnswers.length == selectedAnswers.size() && selectedAnswers.containsAll(Arrays.asList(correctAnswers))) return points;
            else return 0;
        }
        else {
            int correct = 0;

            for (int i = 0; i < correctAnswers.length; i++){
                if(selectedAnswers.contains(correctAnswers[i])) correct++;
            }

            if(questionBehaviour == MultipleChoiceQuestionBehaviour.StrictPenalty){
                return correct * points - (selectedAnswers.size() - correct) * points;
            }
            else if(questionBehaviour == MultipleChoiceQuestionBehaviour.Penalty){
                int res = correct * points - (selectedAnswers.size() - correct) * points;

                return Math.max(res, 0);
            }
            else { // Bonus
                if(correct == selectedAnswers.size()) return correct + points;
                else return correct - (selectedAnswers.size() - correct);
            }
        }
    }
}
