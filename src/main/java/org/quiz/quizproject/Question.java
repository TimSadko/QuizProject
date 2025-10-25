package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        //defaultImpl = TrueFalseQuestion.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "TrueFalse"),
        @JsonSubTypes.Type(value = SingleChoiceQuestion.class, name = "SingleChoice"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "MultipleChoice"),
        @JsonSubTypes.Type(value = TextQuestion.class, name = "Text")
})
public abstract class Question {
    public abstract boolean getIsAnswerSelected();
    public abstract String getText();
    public abstract QuestionType getType();
    public abstract int calcPoints();
}
