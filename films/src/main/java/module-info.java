module com.github.maxbrt.films {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.maxbrt.films to javafx.fxml;
    exports com.github.maxbrt.films;
}