package com.github.maxbrt.films;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import atlantafx.base.theme.CupertinoDark;

import com.github.maxbrt.films.utils.DatabaseSeeder;
import com.github.maxbrt.films.utils.HibernateUtil;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize Hibernate (triggers schema update)
        HibernateUtil.getSessionFactory();

        // Seed default data only when --seed flag is passed
        if (getParameters().getRaw().contains("--seed")) {
            DatabaseSeeder.flush();
            DatabaseSeeder.seedGenres();
            DatabaseSeeder.seedContenus();
        }

        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Films");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }
}
