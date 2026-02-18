package com.github.maxbrt.films.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import com.github.maxbrt.films.model.Contenu;

public class ContenuCard extends VBox {

    public ContenuCard(Contenu c, Node actionsPane) {
        super(8);
        setPrefWidth(200);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-radius: 8; -fx-border-width: 1;" +
                "-fx-border-color: #0B81F8;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 6, 0, 0, 2);");

        getChildren().addAll(buildPosterStack(c), buildInfo(c), actionsPane);
    }

    private StackPane buildPosterStack(Contenu c) {
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
        synopsisLabel.setOpacity(0);

        StackPane posterStack = new StackPane(poster, synopsisLabel);
        posterStack.setOnMouseEntered(e -> synopsisLabel.setOpacity(1));
        posterStack.setOnMouseExited(e -> synopsisLabel.setOpacity(0));

        return posterStack;
    }

    private VBox buildInfo(Contenu c) {
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

        Label note = new Label("\u2605 " + c.getNote() + "/5");
        note.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        info.getChildren().addAll(titre, genre, meta, note);

        return info;
    }
}
