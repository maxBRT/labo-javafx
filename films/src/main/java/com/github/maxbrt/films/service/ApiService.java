package com.github.maxbrt.films.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.Genre;
import com.github.maxbrt.films.model.GenreApi;
import com.github.maxbrt.films.model.GenreApiResponse;
import com.github.maxbrt.films.model.MovieApi;
import com.github.maxbrt.films.model.MovieApiResponse;
import com.github.maxbrt.films.repository.GenreRepository;
import com.github.maxbrt.films.utils.HibernateUtil;

public class ApiService {
    private final ApiClient client;
    private Map<Integer, String> genreMap = new HashMap<>();
    private final GenreRepository genreRepository = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);

    public ApiService(String baseUrl, String apiKey) {
        this.client = new ApiClient(baseUrl, apiKey);
    }

    public List<Contenu> getPopularMovies() throws Exception {
        loadGenre();
        Random random = new Random();
        int page = random.nextInt(6);
        MovieApiResponse response = client.sendGetRequest("/movie/popular?language=fr-CA&page=" + page,
                MovieApiResponse.class);
        List<MovieApi> movies = response.getMovies();
        List<Contenu> contenus = new ArrayList<>();
        for (MovieApi movie : movies) {
            contenus.add(mapMovieToContenu(movie));
        }
        return contenus;
    }

    private void loadGenre() throws Exception {
        GenreApiResponse response = client.sendGetRequest("/genre/movie/list?language=fr-CA", GenreApiResponse.class);
        for (GenreApi g : response.getGenres()) {
            genreMap.put(g.getId(), g.getName());
        }
    }

    private Contenu mapMovieToContenu(MovieApi movie) {
        Genre genre = genreRepository.findOrCreateByName(genreMap.get(movie.getGenreId()));
        return new Contenu(
                movie.getTitle(),
                "Film",
                movie.getReleaseYear(),
                "",
                movie.getOverview(),
                movie.getFullPosterUrl(),
                "A voir",
                0,
                true,
                genre);
    }

}
