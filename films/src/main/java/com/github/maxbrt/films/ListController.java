package com.github.maxbrt.films;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

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
            cardsContainer.getChildren().add(createCard(c));
        }
    }

    private VBox createCard(Contenu c) {
        VBox card = new VBox(8);
        card.setPrefWidth(200);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-border-radius: 8; -fx-border-width: 1;" +
                "-fx-border-color: #e0e0e0;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 6, 0, 0, 2);");

        ImageView poster = new ImageView();
        poster.setFitWidth(200);
        poster.setFitHeight(280);
        poster.setPreserveRatio(false);
        poster.setSmooth(true);

        String imageUrl = c.getImageUrl();
        if (imageUrl != null && !imageUrl.isBlank()) {
            poster.setImage(new Image(imageUrl, 200, 280, false, true, true));
        }

        Label synopsisLabel = new Label(c.getSynopsis() != null ? c.getSynopsis() : "");
        synopsisLabel.setWrapText(true);
        synopsisLabel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.75);" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 10;" +
                        "-fx-font-size: 14px;");
        synopsisLabel.setPrefSize(200, 280);
        synopsisLabel.setAlignment(Pos.TOP_LEFT);
        // I'm using a stack pane to show the the synopsis on top of the poster
        // And I set the opacity to 0 so it's hidden by default
        synopsisLabel.setOpacity(0);
        StackPane posterStack = new StackPane(poster, synopsisLabel);

        // When the mouse enters the poster, I show the synopsis
        posterStack.setOnMouseEntered(e -> synopsisLabel.setOpacity(1));
        posterStack.setOnMouseExited(e -> synopsisLabel.setOpacity(0));

        VBox info = new VBox(4);
        info.setStyle("-fx-padding: 8;");

        Label titre = new Label(c.getTitre());
        titre.setWrapText(true);
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label genre = new Label(c.getGenre() != null ? c.getGenre().getNom() : "Sans genre");
        genre.setStyle(
                "-fx-background-radius: 4;" +
                        "-fx-padding: 2 6;" +
                        "-fx-font-size: 11px;");

        Label meta = new Label(c.getType() + (c.getAnneeSortie() > 0 ? " - " + c.getAnneeSortie() : ""));
        meta.setStyle("-fx-text-fill: #666; -fx-font-size: 11px;");

        if (c.getRealisateur() != null && !c.getRealisateur().isBlank()) {
            Label realisateur = new Label(c.getRealisateur());
            realisateur.setWrapText(true);
            realisateur.setStyle("-fx-font-size: 11px; -fx-font-style: italic;");
            info.getChildren().add(realisateur);
        }

        Label note = new Label("★ " + c.getNote() + "/5");
        note.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        info.getChildren().addAll(titre, genre, meta, note);

        ComboBox<String> statutCombo = new ComboBox<>();
        statutCombo.getItems().addAll("A voir", "En cours", "Vu");
        statutCombo.setValue(c.getStatut());
        statutCombo.setMaxWidth(Double.MAX_VALUE);
        statutCombo.setStyle("-fx-font-size: 13px;");
        statutCombo.setOnAction(e -> {
            c.setStatut(statutCombo.getValue());
            contenuRepo.update(c);
        });
        info.getChildren().add(statutCombo);

        String btnStyle = "-fx-font-size: 11px; -fx-cursor: hand;";

        VBox actions = new VBox(4);
        actions.setPadding(new Insets(0, 8, 8, 8));

        Button editBtn = new Button("Modifier");
        editBtn.setMaxWidth(Double.MAX_VALUE);
        editBtn.setStyle(btnStyle);
        editBtn.setOnAction(e -> {
            if (onEditRequest != null) {
                onEditRequest.accept(c);
            }
        });

        Button watchlistBtn = new Button(c.isWatchlist() ? "✓ Watchlist" : "+ Watchlist");
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

        actions.getChildren().addAll(editBtn, watchlistBtn, deleteBtn);

        card.getChildren().addAll(posterStack, info, actions);
        return card;
    }
}
