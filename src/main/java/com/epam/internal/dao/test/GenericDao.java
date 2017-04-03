package com.epam.internal.dao.test;

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


    public void create(T newInstance) {
        getSessionFactory().openSession().save(newInstance);
    }

    public T read(Long id) {
        T instance = getSessionFactory().openSession().get(type, id);
        return instance;
    }

    public void update(T transientObject) {
        getSessionFactory().openSession().update(transientObject);
    }

    public void delete(T persistentObject) {
        getSessionFactory().openSession().delete(persistentObject);
    }

    public List<T> getAll() {
        List<T> instances = getSessionFactory().openSession().createQuery("from " + type.getName()).list();
        return instances;
    }

}
