package com.github.maxbrt.films.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.github.maxbrt.films.model.Genre;

/* This is the repository inherit CRUD operations from AbstractRepository */
public class GenreRepository extends AbstractRepository<Genre, Integer> {

    public GenreRepository(SessionFactory sessionFactory, Class<Genre> entityClass) {
        super(sessionFactory, entityClass);
    }

    // Helper for when we fetch from the API.
    // This method allows me to just get the genre or
    // create it if it doesn'texist
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
