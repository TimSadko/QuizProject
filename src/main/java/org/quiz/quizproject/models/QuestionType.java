package org.quiz.quizproject.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType {
    TrueFalse, SingleChoice, MultipleChoice, Text;

    private static Map<String, QuestionType> namesMap = new HashMap<String, QuestionType>(3);

    static {
        namesMap.put("TrueFalse", TrueFalse);
        namesMap.put("SingleChoice", SingleChoice);
        namesMap.put("MultipleChoice", MultipleChoice);
        namesMap.put("Text", Text);
    }

    @JsonCreator
    public static QuestionType forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, QuestionType> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
