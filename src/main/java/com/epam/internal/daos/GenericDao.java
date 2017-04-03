package com.epam.internal.daos;

import org.hibernate.SessionFactory;

import java.util.List;

public class GenericDao<T> {

    private Class<T> type;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public void create(T entity) {
        getSessionFactory().openSession().save(entity);
    }

    public T findyById(Long id) {
        return getSessionFactory().openSession().get(type, id);
    }

    public void update(T entity) {
        getSessionFactory().openSession().update(entity);
    }

    public void delete(T entity) {
        getSessionFactory().openSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getSessionFactory().openSession().createQuery("from " + type.getName()).list();
    }

}
