module com.github.maxbrt.films {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.net.http;
    requires atlantafx.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires io.github.cdimascio.dotenv.java;

    opens com.github.maxbrt.films to javafx.fxml;
    opens com.github.maxbrt.films.controllers to javafx.fxml;
    opens com.github.maxbrt.films.model to org.hibernate.orm.core, com.fasterxml.jackson.databind;

    exports com.github.maxbrt.films;
}
