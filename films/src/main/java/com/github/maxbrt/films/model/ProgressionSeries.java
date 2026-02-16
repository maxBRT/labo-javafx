package com.github.maxbrt.films.model;

import jakarta.persistence.*;

@Entity
@Table(name = "progression_series")
public class ProgressionSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // This links the progression back to the main content
    @OneToOne
    @JoinColumn(name = "contenu_id", nullable = false)
    private Contenu contenu;

    private int saisonsTotales;
    private int saisonsVues;
    private int episodesTotaux;
    private int episodesVus;

    public ProgressionSeries() {
    }

    public ProgressionSeries(int saisonsTotales, int saisonsVues, int episodesTotaux, int episodesVus) {
        this.saisonsTotales = saisonsTotales;
        this.saisonsVues = saisonsVues;
        this.episodesTotaux = episodesTotaux;
        this.episodesVus = episodesVus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contenu getContenu() {
        return contenu;
    }

    public void setContenu(Contenu contenu) {
        this.contenu = contenu;
    }

    public int getSaisonsTotales() {
        return saisonsTotales;
    }

    public void setSaisonsTotales(int saisonsTotales) {
        this.saisonsTotales = saisonsTotales;
    }

    public int getSaisonsVues() {
        return saisonsVues;
    }

    public void setSaisonsVues(int saisonsVues) {
        this.saisonsVues = saisonsVues;
    }

    public int getEpisodesTotaux() {
        return episodesTotaux;
    }

    public void setEpisodesTotaux(int episodesTotaux) {
        this.episodesTotaux = episodesTotaux;
    }

    public int getEpisodesVus() {
        return episodesVus;
    }

    public void setEpisodesVus(int episodesVus) {
        this.episodesVus = episodesVus;
    }
}
