package com.github.maxbrt.films.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import java.time.LocalDateTime;

public class Contenu {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty titre = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final IntegerProperty anneeSortie = new SimpleIntegerProperty();
    private final StringProperty realisateur = new SimpleStringProperty();
    private final StringProperty synopsis = new SimpleStringProperty();
    private final StringProperty imageUrl = new SimpleStringProperty();
    private final IntegerProperty genreId = new SimpleIntegerProperty();
    private final StringProperty statut = new SimpleStringProperty();
    private final IntegerProperty note = new SimpleIntegerProperty();
    private final BooleanProperty watchlist = new SimpleBooleanProperty();
    private final ObjectProperty<LocalDateTime> dateAjout = new SimpleObjectProperty<>();
    private final StringProperty genre = new SimpleStringProperty();
    private final ObjectProperty<ProgressionSeries> progressionSeries = new SimpleObjectProperty<>();

    public Contenu(int id, String titre, String type, int anneeSortie, String realisateur,
            String synopsis, String imageUrl, int genreId, String statut,
            int note, boolean watchlist, LocalDateTime dateAjout, String genre, ProgressionSeries progressionSeries) {
        this.id.set(id);
        this.titre.set(titre);
        this.type.set(type);
        this.anneeSortie.set(anneeSortie);
        this.realisateur.set(realisateur);
        this.synopsis.set(synopsis);
        this.imageUrl.set(imageUrl);
        this.genreId.set(genreId);
        this.statut.set(statut);
        this.note.set(note);
        this.watchlist.set(watchlist);
        this.dateAjout.set(dateAjout);
        this.genre.set(genre);
        this.progressionSeries.set(progressionSeries);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String value) {
        titre.set(value);
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getAnneeSortie() {
        return anneeSortie.get();
    }

    public void setAnneeSortie(int value) {
        anneeSortie.set(value);
    }

    public IntegerProperty anneeSortieProperty() {
        return anneeSortie;
    }

    public String getRealisateur() {
        return realisateur.get();
    }

    public void setRealisateur(String value) {
        realisateur.set(value);
    }

    public StringProperty realisateurProperty() {
        return realisateur;
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public void setImageUrl(String value) {
        imageUrl.set(value);
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public String getStatut() {
        return statut.get();
    }

    public void setStatut(String value) {
        statut.set(value);
    }

    public StringProperty statutProperty() {
        return statut;
    }

    public int getNote() {
        return note.get();
    }

    public void setNote(int value) {
        note.set(value);
    }

    public IntegerProperty noteProperty() {
        return note;
    }

    public boolean isWatchlist() {
        return watchlist.get();
    }

    public void setWatchlist(boolean value) {
        watchlist.set(value);
    }

    public BooleanProperty watchlistProperty() {
        return watchlist;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String value) {
        genre.set(value);
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public ProgressionSeries getProgressionSeries() {
        return progressionSeries.get();
    }

    public void setProgressionSeries(ProgressionSeries value) {
        progressionSeries.set(value);
    }

    public ObjectProperty<ProgressionSeries> progressionSeriesProperty() {
        return progressionSeries;
    }
}
