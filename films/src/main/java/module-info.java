module com.github.maxbrt.films {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires javafx.base;

    opens com.github.maxbrt.films to javafx.fxml;

    exports com.github.maxbrt.films;
}
