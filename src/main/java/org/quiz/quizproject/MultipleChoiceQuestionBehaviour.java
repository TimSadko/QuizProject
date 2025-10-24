package org.quiz.quizproject;

public enum MultipleChoiceQuestionBehaviour {
    StrictAll, // Gives all points if all options selected correctly. If not then no points
    StrictPenalty, // Gives points amount for each correctly selected, and takes away points amount for each wrong answer (can go negative)
    Penalty, // Gives points amount for each correctly selected, and takes away points amount for each wrong answer (can not go negative)
    Bonus // Gives 1 point for each correct answer, takes away 1 point for each incorrect answer.
    // - If there are no incorrect answers then gain additional points equal to points amount.
}
