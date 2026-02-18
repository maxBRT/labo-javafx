package com.github.maxbrt.films.repository;

import org.hibernate.SessionFactory;

import com.github.maxbrt.films.model.Contenu;

/* This is the repository inherit CRUD operations from AbstractRepository */
public class ContenuRepository extends AbstractRepository<Contenu, Integer> {

    public ContenuRepository(SessionFactory sessionFactory, Class<Contenu> entityClass) {
        super(sessionFactory, entityClass);
    }
}
