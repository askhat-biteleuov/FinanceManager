package com.fm.internal.daos;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GenericDao<T> {

    private Class<T> type;

    @Autowired
    private SessionFactory sessionFactory;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void add(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Transactional(readOnly = true)
    public T getById(long id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }

    @Transactional
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Transactional
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return (List<T>) sessionFactory.getCurrentSession().createQuery("from " + type.getName()).list();
    }

}
