package com.github.maxbrt.films.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.Genre;

public class ContenuRepository extends AbstractRepository<Contenu, Integer> {

    public ContenuRepository(SessionFactory sessionFactory, Class<Contenu> entityClass) {
        super(sessionFactory, entityClass);
    }

    public List<Contenu> findByGenre(Genre genre) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Contenu where genre = :genre", Contenu.class)
                    .setParameter("genre", genre)
                    .list();
        }
    }
}
