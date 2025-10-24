module org.quiz.quizproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.quiz.quizproject to javafx.fxml;
    exports org.quiz.quizproject;
}