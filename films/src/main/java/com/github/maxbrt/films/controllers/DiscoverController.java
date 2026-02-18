package com.github.maxbrt.films.controllers;

import java.util.List;

import com.github.maxbrt.films.components.ContenuCard;
import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.repository.ContenuRepository;
import com.github.maxbrt.films.service.ApiService;
import com.github.maxbrt.films.utils.HibernateUtil;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiscoverController {
    @FXML
    private Button refreshButton;
    @FXML
    private FlowPane cardsContainer;

    private ApiService apiService;
    private ContenuRepository contenuRepo;

    @FXML
    public void initialize() {
        contenuRepo = new ContenuRepository(HibernateUtil.getSessionFactory(), Contenu.class);

        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        String apiUrl = dotenv.get("API_BASE_URL");
        apiService = new ApiService(apiUrl, apiKey);
        loadCards();
    }

    public void loadCards() {
        cardsContainer.getChildren().clear();
        cardsContainer.getChildren().add(new Label("Chargement..."));
        refreshButton.setDisable(true);

        Thread.ofVirtual().start(() -> {
            try {
                List<Contenu> contenus = apiService.getPopularMovies();
                Platform.runLater(() -> {
                    cardsContainer.getChildren().clear();
                    for (Contenu c : contenus) {
                        cardsContainer.getChildren().add(new ContenuCard(c, buildDiscoverActions(c)));
                    }
                    refreshButton.setDisable(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    cardsContainer.getChildren().clear();
                    cardsContainer.getChildren().add(new Label("Erreur !"));
                    refreshButton.setDisable(false);
                });
            }
        });
    }

    private Node buildDiscoverActions(Contenu c) {
        VBox actions = new VBox(4);
        actions.setPadding(new Insets(0, 8, 8, 8));

        Button watchlistBtn = new Button("+ Watchlist");
        watchlistBtn.setMaxWidth(Double.MAX_VALUE);
        watchlistBtn.setStyle("-fx-font-size: 11px; -fx-cursor: hand;");
        watchlistBtn.setOnAction(e -> {
            watchlistBtn.setDisable(true);
            Thread.ofVirtual().start(() -> {
                contenuRepo.save(c);
                Platform.runLater(() -> watchlistBtn.setText("\u2713 Ajouté"));
            });
        });

        actions.getChildren().add(watchlistBtn);
        return actions;
    }

    @FXML
    private void handleRefresh() {
        loadCards();
    }
}
