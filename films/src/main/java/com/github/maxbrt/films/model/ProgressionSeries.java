package com.github.maxbrt.films.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ProgressionSeries {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty contenuId = new SimpleIntegerProperty();
    private final IntegerProperty saisonsTotales = new SimpleIntegerProperty();
    private final IntegerProperty saisonsVues = new SimpleIntegerProperty();
    private final IntegerProperty episodesTotaux = new SimpleIntegerProperty();
    private final IntegerProperty episodesVus = new SimpleIntegerProperty();

    public ProgressionSeries(int id, int contenuId, int saisonsTotales, int saisonsVues,
            int episodesTotaux, int episodesVus) {
        this.id.set(id);
        this.contenuId.set(contenuId);
        this.saisonsTotales.set(saisonsTotales);
        this.saisonsVues.set(saisonsVues);
        this.episodesTotaux.set(episodesTotaux);
        this.episodesVus.set(episodesVus);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getContenuId() {
        return contenuId.get();
    }

    public void setContenuId(int value) {
        contenuId.set(value);
    }

    public IntegerProperty contenuIdProperty() {
        return contenuId;
    }

    public int getSaisonsTotales() {
        return saisonsTotales.get();
    }

    public void setSaisonsTotales(int value) {
        saisonsTotales.set(value);
    }

    public IntegerProperty saisonsTotalesProperty() {
        return saisonsTotales;
    }

    public int getSaisonsVues() {
        return saisonsVues.get();
    }

    public void setSaisonsVues(int value) {
        saisonsVues.set(value);
    }

    public IntegerProperty saisonsVuesProperty() {
        return saisonsVues;
    }

    public int getEpisodesTotaux() {
        return episodesTotaux.get();
    }

    public void setEpisodesTotaux(int value) {
        episodesTotaux.set(value);
    }

    public IntegerProperty episodesTotauxProperty() {
        return episodesTotaux;
    }

    public int getEpisodesVus() {
        return episodesVus.get();
    }

    public void setEpisodesVus(int value) {
        episodesVus.set(value);
    }

    public IntegerProperty episodesVusProperty() {
        return episodesVus;
    }
}
