package com.github.maxbrt.films.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class GenreApiResponse {
    private List<GenreApi> genres;

    public List<GenreApi> getGenres() {
        return genres;
    }
}
