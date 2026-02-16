package com.github.maxbrt.films;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.github.maxbrt.films.service.HibernateUtil;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize Hibernate (triggers schema update)
        HibernateUtil.getSessionFactory();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }
}
