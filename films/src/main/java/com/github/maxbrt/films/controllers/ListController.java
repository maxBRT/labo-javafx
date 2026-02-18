package com.github.maxbrt.films.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

import com.github.maxbrt.films.components.ContenuCard;
import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.Genre;
import com.github.maxbrt.films.repository.ContenuRepository;
import com.github.maxbrt.films.repository.GenreRepository;
import com.github.maxbrt.films.utils.HibernateUtil;

public class ListController {

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> genreFilter;
    @FXML
    private ComboBox<String> typeFilter;
    @FXML
    private FlowPane cardsContainer;

    private ContenuRepository contenuRepo;
    private List<Contenu> allContenus;
    private Consumer<Contenu> onEditRequest;

    @FXML
    public void initialize() {
        contenuRepo = new ContenuRepository(HibernateUtil.getSessionFactory(), Contenu.class);
        GenreRepository genreRepo = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);
        List<Genre> genres = genreRepo.findAll();

        genreFilter.getItems().add("Tous");
        for (Genre g : genres) {
            genreFilter.getItems().add(g.getNom());
        }
        genreFilter.setValue("Tous");

        typeFilter.getItems().addAll("Tous", "Film", "Série");
        typeFilter.setValue("Tous");

        loadContenus();

        searchField.textProperty().addListener((obs, old, val) -> refreshCards());
        genreFilter.setOnAction(e -> refreshCards());
        typeFilter.setOnAction(e -> refreshCards());
    }

    // This controller doesn't need to know about the form controller, so I just
    // give it a callback it can call when the user wants to edit a content
    public void setOnEditRequest(Consumer<Contenu> onEditRequest) {
        this.onEditRequest = onEditRequest;
    }

    public void loadContenus() {
        allContenus = contenuRepo.findAll();
        refreshCards();
    }

    private void refreshCards() {
        cardsContainer.getChildren().clear();

        String search = searchField.getText() != null ? searchField.getText().toLowerCase() : "";
        String selectedGenre = genreFilter.getValue();
        String selectedType = typeFilter.getValue();

        List<Contenu> filtered = allContenus.stream()
                .filter(c -> search.isEmpty() || c.getTitre().toLowerCase().contains(search))
                .filter(c -> "Tous".equals(selectedGenre)
                        || (c.getGenre() != null && c.getGenre().getNom().equals(selectedGenre)))
                .filter(c -> "Tous".equals(selectedType) || selectedType.equals(c.getType()))
                .toList();

        for (Contenu c : filtered) {
            cardsContainer.getChildren().add(new ContenuCard(c, buildListActions(c)));
        }
    }

    // At first, this was just hard-coded in the card component, but I wanted to be
    // able reuse the card in the discover tab, so now I can just create a Node with
    // whatever buttons and actions and just give it to the card. Pretty cool!
    private Node buildListActions(Contenu c) {
        String btnStyle = "-fx-font-size: 11px; -fx-cursor: hand;";
        VBox actions = new VBox(4);
        actions.setPadding(new Insets(0, 8, 8, 8));

        ComboBox<String> statutCombo = new ComboBox<>();
        statutCombo.getItems().addAll("A voir", "En cours", "Vu");
        statutCombo.setValue(c.getStatut());
        statutCombo.setMaxWidth(Double.MAX_VALUE);
        statutCombo.setStyle("-fx-font-size: 13px;");
        statutCombo.setOnAction(e -> {
            c.setStatut(statutCombo.getValue());
            contenuRepo.update(c);
        });

        Button editBtn = new Button("Modifier");
        editBtn.setMaxWidth(Double.MAX_VALUE);
        editBtn.setStyle(btnStyle);
        editBtn.setOnAction(e -> {
            if (onEditRequest != null)
                onEditRequest.accept(c);
        });

        Button watchlistBtn = new Button(c.isWatchlist() ? "\u2713 Watchlist" : "+ Watchlist");
        watchlistBtn.setMaxWidth(Double.MAX_VALUE);
        watchlistBtn.setStyle(btnStyle);
        watchlistBtn.setOnAction(e -> {
            c.setWatchlist(!c.isWatchlist());
            contenuRepo.update(c);
            loadContenus();
        });

        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setStyle(btnStyle);
        deleteBtn.setOnAction(e -> {
            contenuRepo.delete(c.getId());
            loadContenus();
        });

        actions.getChildren().addAll(statutCombo, editBtn, watchlistBtn, deleteBtn);
        return actions;
    }
}
