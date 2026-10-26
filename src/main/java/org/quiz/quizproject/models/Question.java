package org.quiz.quizproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.StringProperty;

import java.util.Random;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        //defaultImpl = TrueFalseQuestion.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "boolean"),
        @JsonSubTypes.Type(value = SingleChoiceQuestion.class, name = "radiogroup"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "radiogroup-multi"),
        @JsonSubTypes.Type(value = TextQuestion.class, name = "text")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Question {
    public abstract boolean getIsAnswerSelected();
    public abstract StringProperty textProperty();
    public abstract String getTitle();
    public abstract QuestionType getType();
    public abstract boolean isAnswerCorrect();
    // public abstract int calcPoints();

    public abstract String getName();

    public abstract boolean isRequired();

    public static void shuffleArray(String[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
