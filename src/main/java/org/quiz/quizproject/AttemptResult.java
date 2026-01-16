package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttemptResult {
    private String playerName;
    private int points;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @JsonIgnore
    private final SimpleStringProperty propertyName = new SimpleStringProperty();
    @JsonIgnore
    private final SimpleStringProperty propertyScorePercent = new SimpleStringProperty();
    @JsonIgnore
    private final SimpleStringProperty propertyDate = new SimpleStringProperty();

    public String getPropertyName() {
        return propertyName.get();
    }

    public SimpleStringProperty propertyNameProperty() {
        return propertyName;
    }

    public String getPropertyScorePercent() {
        return propertyScorePercent.get();
    }

    public SimpleStringProperty propertyScorePercentProperty() {
        return propertyScorePercent;
    }

    public String getPropertyDate() {
        return propertyDate.get();
    }

    public SimpleStringProperty propertyDateProperty() {
        return propertyDate;
    }

    public AttemptResult(){}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        propertyName.set(playerName);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        propertyDate.set(df.format(date));
    }
}
