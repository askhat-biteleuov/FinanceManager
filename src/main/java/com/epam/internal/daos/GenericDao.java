package com.epam.internal.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void create(T entity) {
        Session currentSession = sessionFactory.openSession();
        currentSession.save(entity);
        currentSession.close();
    }

    public T findyById(long id) {
        Session currentSession = sessionFactory.openSession();
        T entity = currentSession.get(type, id);
        currentSession.close();
        return entity;
    }

    public void update(T entity) {
        Session currentSession = sessionFactory.openSession();
        currentSession.update(entity);
        currentSession.close();
    }

    public void delete(T entity) {
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
        currentSession.delete(entity);
        currentSession.flush();
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session currentSession = sessionFactory.openSession();
        List<T> list = currentSession.createQuery("from " + type.getName()).list();
        currentSession.close();
        return list;
    }

}
