module com.github.maxbrt.films {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires atlantafx.base;

    opens com.github.maxbrt.films to javafx.fxml;
    opens com.github.maxbrt.films.model to org.hibernate.orm.core;

    exports com.github.maxbrt.films;
}
