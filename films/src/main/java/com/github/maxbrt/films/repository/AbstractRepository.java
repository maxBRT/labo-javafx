package com.github.maxbrt.films.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

/* Here I create an abstract repo with generics to handle basic CRUD.
 * That way, any repository can inherit for it and have all the basic CRUD by default 
*/
public abstract class AbstractRepository<T, ID> {

    protected final SessionFactory sessionFactory;
    public final Class<T> entityClass;

    protected AbstractRepository(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        executeInsideTransaction(session -> session.persist(entity));
    }

    public void update(T entity) {
        executeInsideTransaction(session -> session.merge(entity));
    }

    public void delete(ID id) {
        executeInsideTransaction(session -> {
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
        });
    }

    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    public void deleteAll() {
        executeInsideTransaction(
                session -> session.createMutationQuery("delete from " + entityClass.getName()).executeUpdate());
    }

    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        }
    }

    // Helper to reduce boilerplate
    // Consumer is basically a type for callback with no return value
    // calling .accept(session) is the same as calling session.doSomething()
    protected void executeInsideTransaction(java.util.function.Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }
}
