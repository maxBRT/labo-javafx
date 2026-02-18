package com.github.maxbrt.films.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.github.maxbrt.films.model.Genre;

public class GenreRepository extends AbstractRepository<Genre, Integer> {

    public GenreRepository(SessionFactory sessionFactory, Class<Genre> entityClass) {
        super(sessionFactory, entityClass);
    }

    public Genre findOrCreateByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Genre genre = session.createQuery("from Genre where nom = :nom", Genre.class)
                        .setParameter("nom", name)
                        .uniqueResult();

                if (genre == null) {
                    genre = new Genre(name);
                    session.persist(genre);
                }

                tx.commit();
                return genre;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
