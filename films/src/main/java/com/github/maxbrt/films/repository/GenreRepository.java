package com.github.maxbrt.films.repository;

import org.hibernate.SessionFactory;

import com.github.maxbrt.films.model.Genre;

public class GenreRepository extends AbstractRepository<Genre, Integer> {

    public GenreRepository(SessionFactory sessionFactory, Class<Genre> entityClass) {
        super(sessionFactory, entityClass);
    }
}
