package com.github.maxbrt.films.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "contenus")
public class Contenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String type;

    private int anneeSortie;
    private String realisateur;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    private String imageUrl;
    private String statut;
    private int note;
    private boolean watchlist;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateAjout;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Contenu() {
    }

    public Contenu(String titre, String type, int anneeSortie, String realisateur,
            String synopsis, String imageUrl, String statut, int note,
            boolean watchlist, Genre genre) {
        this.titre = titre;
        this.type = type;
        this.anneeSortie = anneeSortie;
        this.realisateur = realisateur;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.statut = statut;
        this.note = note;
        this.watchlist = watchlist;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

}
